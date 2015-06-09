package org.jboss.qe.subsys.extension;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.registry.ManagementResourceRegistration;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;


/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class SubsystemExtension implements Extension {

    public static final String NAMESPACE = "urn:pkremens:kremilek:1.0";

    // Properties used for model creation.
    public static final String SUBSYSTEM_NAME = "kremilek";
    public static final String CHILD_NAME_IN_XML = "config";
    public static final String CHILD_ELEMENT_NAME = "child";
    public static final String ATTRIBUTE_NAME = "strings";

    // The parser used for parsing our subsystem
    private final SubsystemParser parser = new SubsystemParser();

    protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
    static final PathElement ELEMENT_PATH = PathElement.pathElement(CHILD_NAME_IN_XML, CHILD_ELEMENT_NAME);
    private static final String RESOURCE_NAME = SubsystemExtension.class.getPackage().getName() + ".LocalDescriptions";

    static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix) {
        String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
        return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME, SubsystemExtension.class.getClassLoader(), true, false);
    }

    @Override
    public void initializeParsers(ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, parser);
    }


    @Override
    public void initialize(ExtensionContext context) {
        //register subsystem with its model version
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, 1, 0);
        //register subsystem model with subsystem definition that defines all attributes and operations
        final ManagementResourceRegistration registration = subsystem.registerSubsystemModel(SubsystemDefinition.INSTANCE);
        //register describe operation, note that this can be also registered in SubsystemDefinition
        registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
        //we can register additional submodels here
        subsystem.registerXMLElementWriter(SubsystemParser.INSTANCE);
    }
}
