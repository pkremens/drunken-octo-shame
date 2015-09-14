import org.junit.BeforeClass;

/**
 * @author Petr Kremensky pkremens@redhat.com on 03/09/2015
 */
public class JarDomainTestCase extends AbstractEmbedServerTestCase {
    private static final String CLI_JAR = JBOSS_HOME + "/bin/client/jboss-cli-client.jar";

    /**
     * Set the requested variables for running the tests. Prepare arguments to process builder instance which will be
     * reused by the tests for starting the CLI process.
     */
    @BeforeClass
    public static void prepareProcess() {
        embeddedStartCommnad = EMBED_HOST_CONTROLLER;
        varsCheck();

        pb = new ProcessBuilder(buildArgs("java", "-jar", CLI_JAR, AESH_ARGS));
        pb.redirectErrorStream(true);
    }




    @Override
    void standardServerStart() throws Exception {
        String arg = " --jboss-home=" + JBOSS_HOME;
        sendLine(EMBED_HOST_CONTROLLER + arg);
        waitFor(DOMAIN_EMBEDDED);
    }
}
