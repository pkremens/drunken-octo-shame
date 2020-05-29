package org.jboss.qe.jdk8.lambda;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Petr Kremensky pkremens@redhat.com on 07/03/2016
 */
public class Debug {
    private static final String text = "aa 11" +
            "\nab 12" +
            "\nbb 22";

    private static boolean contains22(String text) {
        System.out.println("Put break-point here: " + text);
        return text.contains("22");
    }


    public static void main(String[] args) {
        System.out.println("1) predicate in method");
        System.out.println(Arrays.stream(text.split("\n"))
                .filter(Debug::contains22)
                .collect(Collectors.toSet()));

        System.out.println("\n2) groovy style lambda");
        System.out.println(Arrays.stream(text.split("\n"))
                .filter(line -> line.contains("22"))
                .collect(Collectors.toSet()));

        System.out.println("\n3) block lambda");
        System.out.println(Arrays.stream(text.split("\n"))
                .filter(line -> {
                    boolean cont = line.contains("22");
                    System.out.println("Put break-point here: " + line);
                    return cont;
                })
                .collect(Collectors.toSet()));
    }
}
