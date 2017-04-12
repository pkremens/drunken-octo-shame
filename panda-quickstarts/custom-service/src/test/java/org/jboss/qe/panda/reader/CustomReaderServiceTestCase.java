package org.jboss.qe.panda.reader;

import org.jboss.qa.eap.panda.readers.InputFormatReader;
import org.jboss.qa.eap.panda.spi.ServiceFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verify that {@link ServiceFactory} is able to load custom services defined in SPI file.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class CustomReaderServiceTestCase {

    /**
     * Verify that custom service is loaded.
     */
    @Test
    public void serviceIsLoaded() {
        Assert.assertTrue(ServiceFactory.getServiceImplementations(InputFormatReader.class).stream().
                anyMatch(loadedService ->
                        loadedService.getClass() == CustomReaderService.class)
        );
    }

    /**
     * Verify that we are able to instantiate the service.
     */
    @Test
    public void serviceIsUsable() {
        ServiceFactory.getServiceImplementations(InputFormatReader.class);
        InputFormatReader customService = ServiceFactory.createService("test", InputFormatReader.class);
        Assert.assertNull(customService);

        customService = ServiceFactory.createService(CustomReaderService.class.getSimpleName(), InputFormatReader.class);
        Assert.assertNotNull(customService);
    }

}
