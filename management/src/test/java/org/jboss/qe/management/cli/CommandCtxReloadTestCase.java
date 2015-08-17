package org.jboss.qe.management.cli;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;
import org.jboss.dmr.ModelNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Petr Kremensky pkremens@redhat.com on 04/06/2015
 */
public class CommandCtxReloadTestCase {
    private CommandContext ctx;

    @Before
    public void connect() throws CommandLineException {
        ctx = CommandContextFactory.getInstance().newCommandContext();
        ctx.connectController("localhost", 9990);
    }

    @After
    public void terminate() {
        this.ctx.terminateSession();
    }

    @Test
    public void reload() throws Exception {
        System.out.println(cmd("reload").asString());

    }

    public ModelNode cmd(String cliCommand) throws Exception {

        ModelNode ioe = this.ctx.buildRequest(cliCommand);
        ModelNode cle = this.ctx.getModelControllerClient().execute(ioe);
        return cle;

    }
}
