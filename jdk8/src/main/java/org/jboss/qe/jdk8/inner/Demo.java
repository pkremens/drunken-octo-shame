package org.jboss.qe.jdk8.inner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Petr Kremensky pkremens@redhat.com on 06/07/2015
 */
public class Demo {
    public static void main(String[] args) {
        List<String> hellos = new ArrayList<>();
        hellos.add("Ahoj");
        hellos.add("ahojky");
        hellos.add("Hello");
        hellos.add("Ola");
        hellos.add("Gutten tag");

        hellos.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.toLowerCase().contains("ahoj");
            }
        }).forEach(System.out::println);
        System.out.println();
        hellos.stream().filter(n -> n.toLowerCase().contains("ahoj")).forEach(System.out::println);
        System.out.println();


        new Object() {
            // An instance initializer
            {
                System.out.println("Hello from an anonymous class.");
            }
        }; // A semi-colon is necessary to end the statement

        Car car = new Car(123) {
            {
                System.out.println("asd");
            }
        };
    }


}
