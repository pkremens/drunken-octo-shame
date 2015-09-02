import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com on 02/09/2015
 */
public class CLITestWindows {
    private static Process process;
    private static BufferedWriter writer;
    private static CharPumper outPump;
    private static final int TIMEOUT = 10;


    public static void main(String[] args) throws InterruptedException, IOException {
        final File JBOSS_CLI = new File(args[0]);
        assert JBOSS_CLI.exists();

//        String directConsole = null;
////         windows specific
//        if (isWindows()) {
//            directConsole = INSTALLER.getName().contains("7.0.0") ? "-Dinstaller.direct.console=true" : "-Djline.WindowsTerminal.directConsole=false";
//        }

        ProcessBuilder pb = new ProcessBuilder(buildArgs("sh", JBOSS_CLI.getAbsolutePath()));
        pb.redirectErrorStream(true);

        // start process
        try {
            process = pb.start();
        } catch (IOException e) {
            System.err.println("Process failed to start.");
            System.exit(1);
        }

        // consume process input & error streams
        final InputStreamReader isr = new InputStreamReader(process.getInputStream());
        outPump = new CharPumper(isr);
        outPump.start();

        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));


        waitFor("disconnected", "Failed to read the language dialogue.");
        sendLine("embed-server");
        waitFor("[standalone@embedded /]", "blah");
        sendLine("help");
        waitFor("in some cases, values", "blah2");


        process.destroy();
    }

    // null for empty
    private static void sendLine(String line) throws IOException {
        writer.write(line + "\n");
        writer.flush();
    }

    private static void waitFor(String expected, String onFail) throws InterruptedException {
        boolean found = false;
        for (int i = 0; i < TIMEOUT; i++) {
            Thread.sleep(1000L);

            if (outPump.getOutput().contains(expected)) {
                System.out.println(outPump.clearOutput());
                found = true;
                break;
            }
        }

        if (!found) {
            System.err.println(onFail);
            System.exit(1);
        }

    }

    /**
     * Help class for consumption of process output streams.
     */
    private static class CharPumper extends Thread {
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
                System.err.println("Exception caught while reading process output");
            } finally {
                try {
                    if (inputReader != null) {
                        inputReader.close();
                    }
                } catch (IOException ex) {
                    System.err.println("Failed to close InputStreamReader");
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
    protected static String[] buildArgs(String... args) {
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

    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return (osName.toUpperCase().contains("WINDOWS"));

    }
}
