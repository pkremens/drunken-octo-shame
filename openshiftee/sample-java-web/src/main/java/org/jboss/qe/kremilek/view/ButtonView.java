package org.jboss.qe.kremilek.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@RequestScoped
@Named
public class ButtonView {

    @Inject
    private FacesContext facesContext;

    @Inject
    private Logger log;

    @Named
    public void buttonAction() {
        log.info("Button pressed");
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", "Welcome to Primefaces!!");
        facesContext.addMessage(null, m);
    }
}
