package org.jboss.qe.jdk8.funcinterface;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Demo1 {
    public static void main(String[] argv) {
        String name = "Java Lambda";

        Processor stringProcessor = (String str) -> str.indexOf("b");
        System.out.println(stringProcessor.processString(name));

        stringProcessor = (String str) -> str.length();
        System.out.println(stringProcessor.processString(name));
    }

    @FunctionalInterface
    interface Processor {
        int processString(String str);
    }
}
