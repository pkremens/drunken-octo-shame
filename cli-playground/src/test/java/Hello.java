import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Petr Kremensky pkremens@redhat.com on 24/06/2016
 */
public class Hello {

    public static void main(String[] args) throws CommandLineException, IOException {
        final CommandContext ctx = CommandContextFactory.getInstance().newCommandContext();

//        File outputFile = File.createTempFile("output", "txt");
//        PrintStream output = new PrintStream(outputFile);
//        ctx.captureOutput(output);

        ctx.connectController();
        ctx.handle("echo hello");
        ctx.terminateSession();

//        FileInputStream fis = new FileInputStream(outputFile);
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        while (bis.available() != 0) {
//            System.out.print((char) bis.read());
//        }
    }
}
