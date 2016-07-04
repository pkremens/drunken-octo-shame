package org.jboss.qe.programmatical;

import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class EchoCommandTestCase {
    private CommandContext ctx;


    @Before
    public void initContext() throws CliInitializationException {
        ctx = CommandContextFactory.getInstance().newCommandContext();
    }

    @BeforeClass
    public static void setProps() {
        File jbossCliXml = new File("/home/pkremens/workspace/wildfly-core-3.0.0.Alpha3-SNAPSHOT/bin/jboss-cli.xml");
        File logginPorperties = new File("/home/pkremens/workspace/jboss-eap-7.0/bin/jboss-cli-logging.properties");
        System.setProperty("jboss.cli.config", jbossCliXml.getAbsolutePath());
        System.setProperty("logging.configuration", "file:" + logginPorperties.getAbsolutePath());
//        System.setProperty("jboss.cli.log.file", "/home/pkremens/workspace/jboss-eap-7.0/bin/cli.log");
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
    }

    @Test
    public void connect1() throws CommandLineException, IOException {
        File outputFile = File.createTempFile("output", "txt");
        PrintStream output = new PrintStream(outputFile);
        ctx.captureOutput(output);
        ctx.connectController();
        ctx.handle(":read-resource");
        ctx.handle("echo hello");
        ctx.terminateSession();

        FileInputStream fis = new FileInputStream(outputFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        while (bis.available() != 0) {
            System.out.print((char) bis.read());
        }
    }
}
