package org.jboss.qe.subsys.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Wait2CommandHandler extends CommandHandlerWithHelp {
    public static final String NAME = "wait2";

    public Wait2CommandHandler() {
        super(NAME, false);
    }

    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {

        ctx.printLine(String.format("Current values is: %d", ValueHandler.INSTANCE.decrease()));
    }
}