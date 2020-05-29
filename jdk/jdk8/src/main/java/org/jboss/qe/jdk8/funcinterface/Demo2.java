package org.jboss.qe.jdk8.funcinterface;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.mkyong.com/java8/java-8-lambda-comparator-example/s
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class Demo2 {
    public static void main(String[] args) {
        List<Developer> devs = new ArrayList<>();
        Developer d1 = new Developer("ZZ Top");
        Developer d2 = new Developer("AA Down");

        devs.add(d1);
        devs.add(d2);

        System.out.println(devs);
        devs.sort((dev1, dev2) -> dev1.getName().compareTo(dev2.getName()));
        System.out.println(devs);

    }


    static class Developer {
        String name;

        public Developer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Developer{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
