package org.jboss.qa.checker

import org.jboss.qa.checker.model.Computer
import org.jboss.qa.checker.rest.ComputerService



/**
 * Created with IntelliJ IDEA.
 * User: pkremens
 * Date: 8/4/14
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static Map<String, Set<Computer>> harvested = [:]
    private static Set<Computer> expected = []
    private static ComputerService service = new ComputerService();
    private static int totalSuccessful
    private static int totalMissing
    private static int totalExtra
    private static int totalBroken

    public static void main(String[] args) {
        // print help message and exit if args[0] is not a file
        File config
        if (args.length == 0) {
            println "No args specified"
            printHelp()
        }
        config = new File(args[0])
        if (config.exists()) {
            // parse file (get labels, get required hosts)
            parseConfig(config)
        } else {
            println "Failed to find the configuration file"
            printHelp()
        }
        // update expected with beaker *-virt slaves, all of them should have eap-sustaining label
        updateExpected()
        // run check
        harvested.keySet().each { label ->
            runCheck(label)
        }
        printTotal()

        // print slaves without eap-sustaining label
    }

    private static void printHelp() {
        println '''Help message
'''
        System.exit(0)
    }

    private static void parseConfig(File config) {
        // remove empty lines and lines starting with hash sign
        String text = config.text.replaceAll("(?m)^[ \t]*\r?\n", "").replaceAll(~/#.*\r?\n/, "");
        // get labels from config file, get all online slaves
        text.eachLine { line ->
            if (line.startsWith("LABELS")) {
                def matcher = line =~ /"([^"]*)"/
                matcher.each {
                    String label = it[1]
                    harvested.put(label, service.getOnlineSlavesByLabel(label))
                }
            } else {
                // get all required slaves
                if (service.getSlave(line) != null) {
                    expected.add(service.getSlave(line))
                } else {
                    System.err.println("Wrong query, slave=${line} not found.")
                }
            }
        }
    }

    private static void updateExpected() {
        // add all dev[0-9]+-virt[0-9] hosts to expected
        harvested.keySet().each { label ->
            harvested.get(label).each { slave ->
                if (slave.name ==~ ~/dev[\d]+-virt[\d]/) {
                    expected.add(slave)
                }
            }
        }
        // add all (RHEL6|7 && PPC) and HP-UXv3 hosts
        updateWithLabel("EAP-RHEL6-PPC64")
        updateWithLabel("EAP-RHEL7-PPC64")
        updateWithLabel("hpux11v3")
    }

    private static void updateWithLabel(String label) {
        Set<Computer> update = service.getOnlineSlavesByLabel(label)
        harvested.put(label, update)
        expected.addAll(update)
    }

    private static void runCheck(String label) {
        println "\n#####     ${label}     #####"
        // get online slaves with the eap-sustaining label                 todo CHANGE TO eap-sustaining
        Set<Computer> onlineSlaves = service.getOnlineSlavesByLabel("${label} && eap-sustaining")
        getSuccessful(label, onlineSlaves)
        getMissing(label, onlineSlaves)
        getExtra(onlineSlaves)
        getBroken(label)
    }

    private static void getSuccessful(String label, Set<Computer> withLabel) {
        def successful = new HashSet<Computer>(withLabel)
        successful.retainAll(expected)
        println "Number of eap-sustaining slaves with label=${label}: ${successful.size()}"
        successful.each { println " - ${it.name}" }
        totalSuccessful += successful.size()
    }

    private static void getMissing(String label, Set<Computer> onlineSlaves) {
        Set<Computer> missing = new HashSet<Computer>(expected)
        missing.retainAll(harvested.get(label))
        missing.removeAll(onlineSlaves)
        println "Number of slaves with missing eap-sustaining label: ${missing.size()}"
        missing.each { println " - ${it.name}" }
        totalMissing += missing.size()
    }

    private static void getExtra(Set<Computer> onlineSlaves) {
        Set<Computer> extra = new HashSet<Computer>(onlineSlaves)
        extra.removeAll(expected)
        println "Number of slaves with extra eap-sustaining label: ${extra.size()}"
        extra.each { println " - ${it.name}" }
        totalExtra += extra.size()
    }

    private static void getBroken(String label) {
        Set<Computer> broken = new HashSet<Computer>(service.getSlavesByLabel(label))
        broken.removeAll(harvested.get(label))
        println "Number of broken slaves with label=${label}: ${broken.size()}"
        broken.each { println " - ${it.name}" }
        totalBroken += broken.size()
    }

    private static void printTotal() {
        println "\n#####     TOTAL     #####"
        println "Slaves with eap-sustaining label: ${totalSuccessful}"
        println "Missing eap-sustaining label: ${totalMissing}"
        println "Slaves with extra eap-sustaining label: ${totalExtra}"
        println "Total broken slaves found (only labels from configuration were taken into account): ${totalBroken}"
        if (totalMissing + totalExtra > 0) {
            println "\nFound some slaves with missing/extra eap-sustaining label."
            System.exit(1)
        }
    }
}
