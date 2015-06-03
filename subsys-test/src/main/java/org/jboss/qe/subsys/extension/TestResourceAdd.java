package org.jboss.qe.subsys.extension;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;

/**
 * @author Petr Kremensky pkremens@redhat.com on 01/06/2015
 */
public class TestResourceAdd extends AbstractAddStepHandler {
    @Override
    protected void populateModel(ModelNode operation, ModelNode model) throws OperationFailedException {
        TestResourceDefinition.STRING_ATTRIBUTES.validateAndSet(operation, model);
    }

}
