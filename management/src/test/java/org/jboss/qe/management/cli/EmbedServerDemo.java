package org.jboss.qe.management.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;

/**
 * @author Petr Kremensky pkremens@redhat.com on 08/09/2015
 */
public class EmbedServerDemo {
    public static void main(String[] args) throws Exception {
        final File tmpJBossCliFile = File.createTempFile("jboss-cli", ".xml");
        try (PrintWriter pw = new PrintWriter(tmpJBossCliFile)) {
            pw.print("<jboss-cli xmlns='urn:jboss:cli:1.0'></jboss-cli>");
        }
        System.setProperty("jboss.cli.config", tmpJBossCliFile.getAbsolutePath());
        System.setProperty("aesh.terminal", "org.jboss.aesh.terminal.TestTerminal");
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");

        final ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
        final CommandContext ctx = CommandContextFactory.getInstance().newCommandContext(null, null, null, null, consoleOut);

        // works fine
        // ctx.handleSafe("embed-server --std-out=echo --jboss-home=/home/pkremens/workspace/jboss-eap-7.0");

        // Failed to handle 'embed-host-controller --std-out=echo --jboss-home=/home/pkremens/workspace/jboss-eap-7.0':
        // java.lang.NullPointerException
        ctx.handleSafe("embed-host-controller --std-out=echo --jboss-home=/home/pkremens/workspace/jboss-eap-7.0");

        ctx.handleSafe("ls -l");
        System.out.println(new String(consoleOut.toByteArray()));
        ctx.terminateSession();
    }
}
