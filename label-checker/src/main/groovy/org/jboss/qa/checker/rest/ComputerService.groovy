package org.jboss.qa.checker.rest

import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.jboss.qa.checker.model.Computer

/**
 * Created with IntelliJ IDEA.
 * User: pkremens
 * Date: 8/5/14
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
class ComputerService {
    def client = new RESTClient('http://jenkins.mw.lab.eng.bos.redhat.com/hudson/')

    /**
     * Get all slaves from jenkins
     * @return Set of all slaves registered in Jenkins
     */
    Set<Computer> getSlaves() {
        println("Get all slaves")
        Collections.unmodifiableSet(getComputers('computer/api/json'))
    }

    /**
     * Get all slaves with active connection (slave can be temporarily offline)
     * @return Set of all slaves with active connection to Jenkins (slave.jar is running)
     */
    Set<Computer> getOnlineSlaves() {
        println("Get onlive slaves")
        Collections.unmodifiableSet(getComputers('computer/api/json', true))
    }

    /**
     * Get all slaves with the label
     * @param label
     * @return Set of slaves with label
     */
    Set<Computer> getSlavesByLabel(String label) {
        println("Get all slaves with label=\"${label}\"")
        Collections.unmodifiableSet(getComputersByLabel(label))
    }

    /**
     * Get all slaves with active connection (slave can be temporarily offline) with required label
     * @param label
     * @return Set of all slaves with active connection to Jenkins (slave.jar is running) and required label
     */
    Set<Computer> getOnlineSlavesByLabel(String label) {
        println("Get online slaves with label=\"${label}\"")
        Collections.unmodifiableSet(getComputersByLabel(label, true))
    }

    /**
     * Get slave by name
     * @param name Name of slave
     * @return A new Computer instance of slave
     */
    Computer getSlave(String name) {
        def query = "computer/${name}/api/json"
        def resp
        try {
            resp = client.get(path: query)
        } catch (HttpResponseException hre) {
            System.err.println("Wrong query, slave=${name} not found.")
            return null
        }
        assert resp.status == 200  // HTTP response code; 404 means not found, etc.
        new Computer(resp.data)
    }

    private Object getResponsData(String query) {
        def resp
        try {
            resp = client.get(path: query)
        } catch (HttpResponseException hre) {
            println "Unable to call query: ${query}"
            return null
        }
        assert resp.status == 200  // HTTP response code; 404 means not found, etc.
        resp.data
    }

    private Set<Computer> getComputers(String query, mustBeOnline = false) {
        Set<Computer> computers = []
        getResponsData(query).computer.each() { computer ->
            Computer comp = new Computer(computer)
            if (comp.online || !mustBeOnline) {
                computers.add(comp)
            }
        }
        return computers
    }

    private Set<Computer> getComputersByLabel(String label, mustBeOnline = false) {
        Set<Computer> computers = []
        String query = "label/${label}/api/json"
        getResponsData(query).nodes.nodeName.each() { nodeName ->
            Computer comp = getSlave(nodeName)
            if ((comp.online || !mustBeOnline) && comp != null) {
                computers.add(comp)
            }
        }
        return computers
    }
}
