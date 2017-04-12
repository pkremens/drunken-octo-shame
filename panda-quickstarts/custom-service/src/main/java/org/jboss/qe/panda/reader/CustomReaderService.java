package org.jboss.qe.panda.reader;

import org.apache.log4j.Logger;
import org.jboss.qa.eap.panda.readers.InputFormatReader;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class CustomReaderService extends InputFormatReader {
    private static Logger LOGGER = Logger.getLogger(CustomReaderService.class.getSimpleName());

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        return "Almighty AI test results reader. " +
                "It can be used to parse results from all formats known to mankind, " +
                "as well from formats which are yet to be invented. " +
                "Alien formats are not supported yet - support for these will be probably included in version 0.0.2.";
    }

    @Override
    public void readTestsResults(String folder) {
        LOGGER.info("Reading results.");
        processResults();
        LOGGER.info("... working pretty hard, gimme some time man ...");
        processResults();
        LOGGER.info("No issues, congratulations, your product is GA ready!");
    }

    private void processResults() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // psst, do not tell the user, he won't notice
        }
    }
}
