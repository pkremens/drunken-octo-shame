package org.jboss.qe.subsys.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
class IncreaseCommandHandler extends CommandHandlerWithHelp {
    public static final String NAME = "increase";

    public IncreaseCommandHandler() {
        super(NAME, false);
    }

    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {
        ctx.printLine(String.format("Current values is: %d", ValueHandler.INSTANCE.increase()));
    }
}
