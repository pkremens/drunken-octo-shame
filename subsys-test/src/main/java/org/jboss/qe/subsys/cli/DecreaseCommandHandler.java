package org.jboss.qe.subsys.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;

/**
 * @author Petr Kremensky pkremens@redhat.com on 27/05/2015
 */
public class DecreaseCommandHandler extends CommandHandlerWithHelp {
    public static final String NAME = "decrease";

    public DecreaseCommandHandler() {
        super(NAME, false);
    }

    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {
        ctx.printLine(String.format("Current values is: %d", ValueHandler.INSTANCE.decrease()));
    }
}
