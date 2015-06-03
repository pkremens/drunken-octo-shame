package org.jboss.qe.subsys.extension;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.util.List;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

/**
 * @author Petr Kremensky pkremens@redhat.com on 27/05/2015
 */
class SubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>, XMLElementWriter<SubsystemMarshallingContext> {
    protected static final SubsystemParser INSTANCE = new SubsystemParser();
    private static final PersistentResourceXMLDescription xmlDescription;

    static {
        xmlDescription = builder(SubsystemDefinition.INSTANCE)
                .addChild(
                        builder(TestResourceDefinition.INSTANCE)
                                .addAttribute(TestResourceDefinition.JAR_NAMES)
                )
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context) throws XMLStreamException {
        ModelNode model = new ModelNode();
        model.get(SubsystemDefinition.INSTANCE.getPathElement().getKeyValuePair()).set(context.getModelNode());
        xmlDescription.persist(writer, model, SubsystemExtension.NAMESPACE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException {
        xmlDescription.parse(reader, PathAddress.EMPTY_ADDRESS, list);
    }
}