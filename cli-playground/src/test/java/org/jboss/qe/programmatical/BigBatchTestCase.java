package org.jboss.qe.programmatical;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Petr Kremensky pkremens@redhat.com on 27/06/2016
 */
public class BigBatchTestCase {
    private CommandContext ctx;
    private File outputFile;

    @Before
    public void connectNewCommandContext() throws CommandLineException, IOException {
        ctx = CommandContextFactory.getInstance().newCommandContext();
        outputFile = File.createTempFile("output", "txt");
        PrintStream output = new PrintStream(outputFile);
        ctx.captureOutput(output);
        ctx.connectController();
    }

    @After
    public void terminateSession() throws IOException {
        ctx.terminateSession();
        System.out.println("\nCLI OUTPUT");
        try (BufferedReader br = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    @Test
    public void MocBatchPrograpaticallyTest() throws IOException, CommandLineException {
        File file = new File("/home/pkremens/x");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                ctx.handle(line);
            }
        }
    }

    /**
     * All in one
     *
     * @throws IOException
     * @throws CommandLineException
     */
    @Test
    public void LongBatchPrograpaticallyTest() throws IOException, CommandLineException {
        File file = new File("/home/pkremens/x");
        ctx.handle("batch");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!(line.startsWith("batch") || (line.startsWith("run-batch")))) {
                    System.out.println(line);
                    ctx.handle(line);
                }
            }
        }
        ctx.handle("run-batch --verbose");
    }
}
