package org.jboss.qe.subsys.extension;

import org.jboss.as.controller.*;
import org.jboss.dmr.ModelNode;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Petr Kremensky pkremens@redhat.com on 01/06/2015
 */
public class TestResourceDefinition extends PersistentResourceDefinition {

    static final TestResourceDefinition INSTANCE = new TestResourceDefinition();

    static final StringListAttributeDefinition JAR_NAMES = new StringListAttributeDefinition.Builder("jars")
            .setAllowNull(false)
            .setAttributeMarshaller(new DefaultAttributeMarshaller() {
                @Override
                public void marshallAsAttribute(AttributeDefinition attribute, ModelNode resourceModel, boolean marshallDefault, XMLStreamWriter writer) throws
                        XMLStreamException {

                    StringBuilder builder = new StringBuilder();
                    if (resourceModel.hasDefined(attribute.getName())) {
                        for (ModelNode p : resourceModel.get(attribute.getName()).asList()) {
                            builder.append(p.asString()).append(", ");
                        }
                    }
                    if (builder.length() > 3) {
                        builder.setLength(builder.length() - 2);
                    }
                    if (builder.length() > 0) {
                        writer.writeAttribute(attribute.getXmlName(), builder.toString());
                    }
                }
            })
            .build();


    private TestResourceDefinition() {
        super(SubsystemExtension.JAR_BLACKLIST_PATH,
                SubsystemExtension.getResourceDescriptionResolver("jar-blacklist"),
                //new AbstractAddStepHandler(JAR_NAMES),
                new TestResourceAdd(),
                ReloadRequiredRemoveStepHandler.INSTANCE);
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Collections.singleton((AttributeDefinition) JAR_NAMES);
    }
}