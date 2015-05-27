package org.jboss.qe.subsys.extension;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;

/**
 * @author Petr Kremensky pkremens@redhat.com on 26/05/2015
 */
public class SubsystemCommandHandler extends CommandHandlerWithHelp {

    public static final String NAME = "kremilek-hello";
    public static final String OUTPUT = "hello world from Kremilek!";

    public SubsystemCommandHandler() {
        super(NAME, false);
    }

    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {
        ctx.printLine(OUTPUT);
    }
}
