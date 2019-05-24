package org.jboss.qe.kremilek.view;

import org.jboss.qe.kremilek.data.PersonManager;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
@Model
public class ButtonView {

    @Inject
    private FacesContext facesContext;

    @Inject
    private Logger log;
    @Inject
    private PersonManager personManager;

    @Named
    public void welcomeAction() {
        log.info("Welcome button pressed");
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", "Welcome to Primefaces!!");
        facesContext.addMessage(null, m);
    }

    @Named
    public void resetAction() {
        log.info("Reset button pressed");
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reset", "Reset the Members!!");
        facesContext.addMessage(null, m);
        personManager.initPeople();
    }
}
