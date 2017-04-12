# Implementation of custom Service Provide
PANDA is modular system which uses Java Service Provider Interfaces. Please see [SPI-intro](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html) for more information.

If you want to prepare a new implementation of input reader, env param sorter or processor you have to:
  * Add PANDA as dependency for your project
  * Your implementation must inherit from
     * New input format reader has to inherit from `org.jboss.qa.eap.panda.readers.InputFormatReader` service and it has to be registered via Java SPI API.
     * New env param sorter has to implement `org.jboss.qa.eap.panda.uploader.envparamsort.EnvParamSorter` service and has to be regietered via Java SPI API.
     * New post processor has to implement  `org.jboss.qa.eap.panda.postprocessing.PostProcessor` service for general post processor or `org.jboss.qa.eap.panda.postprocessing.DocumentationPostProcessor` for test cases documentation post processor and registered via Java SPI API.
  * In your project create folder META-INF/services
  * Add text file witch contains FQN of your class, name of class corresponds with service
     * New input format reader - `org.jboss.qa.eap.panda.readers.InputFormatReader`
     * New env param sorter - `org.jboss.qa.eap.panda.uploader.envparamsort.EnvParamSorter`
     * New post processor - `org.jboss.qa.eap.panda.postprocessing.PostProcessor`

Java will automatically register your new implementation and PANDA will be able to use it.

Be careful when your project will be delivered as one jar file with all dependencies. Assembly plugin will copy all files from `META-INF\services` from all dependencies into one folder and files with the same name will be overwritten.

## Example content
* [pom.xml](pom.xml) file with Panda added as dependency
* [CustomReaderService](src/main/java/org/jboss/qe/panda/reader/CustomReaderService.java) custom InputFormatReader service
* [org.jboss.qa.eap.panda.readers.InputFormatReader](src/main/resources/META-INF/services/org.jboss.qa.eap.panda.readers.InputFormatReader) service registration
* [CustomServiceServiceDemo](src/main/java/org/jboss/qe/panda/reader/CustomServiceServiceDemo.java) Main class

Main class will print the help content which is created dynamically and shows all registered services.
Execute:
```
mvn package
...
InputFormatReader
Service reads input test result format and parses data into internal DTOs. It stores read data and it provides basic operations for working with read data.
Registered implementations:
  CustomReaderService      - Almighty AI test results reader. It can be used to parse results from all formats known to mankind, as well from formats which are yet to be invented. Alien formats are not supported yet - support for these will be probably included in version 0.0.2.
  JUnit                    - JUnit test results format
  Jenkins                  - Jenkins test results format
  SimpleFormat             - Reads test results from the 1st line of txt files (true|yes|passed|1, false|no|failed|0, error, skipped)
  TCK                      - TCK JTR test results format
  TestNG                   - TestNG test results format

```
Notice that `CustomReaderService` was registered by Panda and is ready to be used.