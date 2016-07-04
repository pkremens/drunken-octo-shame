package org.jboss.qe.programmatical;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.impl.CommandContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Demo {
    public static void main(String[] args) throws IOException, CommandLineException {
        final CommandContextConfiguration ccc = new CommandContextConfiguration.Builder().setEchoCommand(true).build();
        final CommandContext ctx = CommandContextFactory.getInstance().newCommandContext(ccc);

        File outputFile = File.createTempFile("output", "txt");
        PrintStream output = new PrintStream(outputFile);
        ctx.captureOutput(output);
        ctx.connectController();
        ctx.handle("echo hello");
        ctx.terminateSession();

        System.out.println(new String(Files.readAllBytes(outputFile.toPath())));
    }
}
