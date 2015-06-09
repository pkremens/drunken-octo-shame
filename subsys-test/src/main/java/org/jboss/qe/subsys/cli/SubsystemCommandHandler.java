package org.jboss.qe.subsys.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandFormatException;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;
import org.jboss.as.cli.util.HelpFormatter;
import org.jboss.as.protocol.StreamUtils;
import org.wildfly.security.manager.WildFlySecurityManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
class SubsystemCommandHandler extends CommandHandlerWithHelp {

    public static final String NAME = "kremilek-hello";
    public static final String OUTPUT = "Hello world from Kremilek!";

    public SubsystemCommandHandler() {
        super(NAME, false);
    }

    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {
        ctx.printLine(OUTPUT);
    }

    @Override
    protected void printHelp(CommandContext ctx) throws CommandLineException {
        final String filename = "help/" + NAME + ".txt";
        ClassLoader cl = WildFlySecurityManager.getClassLoaderPrivileged(SubsystemCommandHandler.class);
        InputStream helpInput = cl.getResourceAsStream(filename);
        if (helpInput != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(helpInput));
            try {
                HelpFormatter.format(ctx, reader);
            } catch (java.io.IOException e) {
                throw new CommandFormatException("Failed to read help/help.txt: " + e.getLocalizedMessage());
            } finally {
                StreamUtils.safeClose(reader);
            }
        } else {
            throw new CommandFormatException("Failed to locate command description " + filename);
        }
    }

}
