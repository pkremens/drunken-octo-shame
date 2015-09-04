import org.junit.BeforeClass;

/**
 * @author Petr Kremensky pkremens@redhat.com on 03/09/2015
 */
public class ScriptTestCase extends AbstractEmbedServerTestCase {
    private static final String CLI_SCRIPT = JBOSS_HOME + "/bin/jboss-cli.sh";

    /**
     * Set the requested variables for running the tests. Prepare arguments to process builder instance which will be
     * reused by the tests for starting the CLI process.
     */
    @BeforeClass
    public static void prepareProcess() {
        embeddedStartCommnad = EMBED_SERVER;
        varsCheck();

        pb = new ProcessBuilder(buildArgs("sh", CLI_SCRIPT, AESH_ARGS));
        pb.redirectErrorStream(true);
    }


    @Override
    void standardServerStart() throws Exception {
        sendLine(EMBED_SERVER);
        waitFor(STANDALONE_EMBEDDED);
    }
}
