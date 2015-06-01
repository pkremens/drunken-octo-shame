package org.jboss.qe.subsys.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandHandler;
import org.jboss.as.cli.CommandHandlerProvider;

/**
 * @author Petr Kremensky pkremens@redhat.com on 27/05/2015
 */
public class IncreaseCommandHandlerProvider implements CommandHandlerProvider {
    @Override
    public CommandHandler createCommandHandler(CommandContext ctx) {
        return new IncreaseCommandHandler();
    }

    @Override
    public boolean isTabComplete() {
        return true;
    }

    @Override
    public String[] getNames() {
        return new String[]{IncreaseCommandHandler.NAME};
    }

}