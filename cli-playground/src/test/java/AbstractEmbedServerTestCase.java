import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com on 03/09/2015
 */
public abstract class AbstractEmbedServerTestCase {
    // This are just temporary
    static final String JBOSS_HOME = "/home/pkremens/workspace/jboss-eap-7.0";

    // Test constants
    static final int TIMEOUT = 5;
    static final String FS = System.getProperty("file.separator");

    // CLI command constants
    static final String EMBED_SERVER = "embed-server";
    static final String EMBED_HOST_CONTROLLER = "embed-host-controller";
    static final String STOP_EMBED_SERVER = "stop-embed-server";
    static final String STOP_EMBED_HOST_CONTROLLER = "stop-embed-host-controller";

    // CLI output constants
    static final String DISCONNECTED = "[disconnected /]";
    static final String STANDALONE_EMBEDDED = "[standalone@embedded /]";
    static final String DOMAIN_EMBEDDED = "[domain@embedded /]";

    // I/O
    private BufferedWriter writer;
    private CharPumper outPump;
    private Process process;

    // Prepare process parameters for running tests in modular/non-modular class-loading environment
    static final String AESH_ARGS = "-Daesh.terminal=org.jboss.aesh.terminal.TestTerminal -Daesh.ansi=false";
    static ProcessBuilder pb;

    // Specifying the type of execution - domain/standalone
    abstract void standardServerStart() throws Exception;

    // Test variables - each test have to set these
    static String embeddedStartCommnad;

    static void varsCheck() {
        checkVar("embeddedStartCommnad", embeddedStartCommnad);
    }

    /**
     * Check that veriable was set by inheriting class
     *
     * @param varName Name of the variable in sources.
     * @param var     The variable itself.
     */
    private static void checkVar(final String varName, final String var) {
        if (var == null) {
            throw new RuntimeException(String.format("Cannot run the test class without '%s' variable set", varName));
        }

    }

    @Before
    public void startProcess() throws Exception {
        // start process
        try {
            process = pb.start();
        } catch (IOException e) {
            Assert.fail("Process failed to start.");
        }

        // consume process input & error streams
        final InputStreamReader isr = new InputStreamReader(process.getInputStream());
        outPump = new CharPumper(isr);
        outPump.start();
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        // wait for disconnected string implying that CLI process is ready for use
        waitFor(DISCONNECTED);
    }

    @After
    public void cleanUp() throws Exception {
        // minimal set of commands to destroy the process completely on Windows
        process.getOutputStream().close();
        process.destroyForcibly();
        process.waitFor();
    }


    /**
     * Help class for consumption of process output streams.
     */
    private class CharPumper extends Thread {
        // do not use BufferedReader - sometime is the last line of output same as line for input
        private InputStreamReader inputReader;
        private StringBuilder output = new StringBuilder();

        public CharPumper(InputStreamReader outputReader) {
            this.inputReader = outputReader;
        }

        public void run() {
            try {
                int c;
                while ((c = inputReader.read()) != -1) {
                    output.append((char) c);
                }
            } catch (IOException e) {
                // Ignore these later
                System.err.println("Exception caught while reading process output");
                e.printStackTrace();
            } finally {
                try {
                    if (inputReader != null) {
                        inputReader.close();
                    }
                } catch (IOException ex) {
                    System.err.println("Failed to close InputStreamReader");
                    ex.printStackTrace();
                }
            }
        }

        /**
         * Get so far collected output from process output stream.
         *
         * @return So far collected output from process since last clean-up.
         */
        public String getOutput() {
            return output.toString();
        }

        /**
         * Get and clear collected output from process input stream.
         *
         * @return Output collected from process input stream since last clean-up.
         */
        public String clearOutput() {
            String out = output.toString();
            output.setLength(0);
            return out;
        }
    }

    /**
     * Use for building proses arguments. Exclude all args where (arg=null || arg="")
     *
     * @param args Arguments passed to process.
     */
    static String[] buildArgs(final String... args) {
        System.out.println("Building args from: " + Arrays.asList(args));
        List<String> validArgs = new ArrayList<String>(args.length);
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg == null || arg.equals("")) {
                System.out.println("Throw away an empty argument with index: " + i);
            } else {
                validArgs.add(arg);
            }
        }
        System.out.println("Collected valid args: " + validArgs);
        return validArgs.toArray(new String[validArgs.size()]);
    }

    boolean isWindows() {
        String osName = System.getProperty("os.name");
        return (osName.toUpperCase().contains("WINDOWS"));

    }

    // null for empty
    void sendLine(final String line) throws IOException {
        writer.write(line);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }

    void waitFor(final String expected) throws InterruptedException {
        boolean found = false;
        for (int i = 0; i < TIMEOUT * 10; i++) {
            Thread.sleep(100L);


            if (outPump.getOutput().contains(expected)) {
                System.out.println(outPump.clearOutput());
                found = true;
                break;
            }
        }

        Assert.assertTrue(String.format("Failed to found <%s> in :\n%s", expected, outPump.getOutput()), found);

    }


    @Test
    public void test1() throws Exception {
        standardServerStart();
        sendLine("help");
        waitFor("in some cases, values");
    }

    @Test
    public void test2() throws Exception {
        standardServerStart();
        sendLine("help");
        waitFor("in some cases, values");
    }

    @Test
    public void test3() throws Exception {
        standardServerStart();
        sendLine("help");
        waitFor("in some cases, values");
        sendLine("exit");
    }

    @Test
    public void test4() throws Exception {
        standardServerStart();
        sendLine("help");
        waitFor("in some cases, values");
        sendLine("quit");
    }

}
