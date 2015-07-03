package org.jboss.qe.jdk8.lambda;

import java.util.stream.IntStream;

/**
 * @author Petr Kremensky pkremens@redhat.com on 23/06/2015
 */
public class Demo2 {
    public static void main(String[] args) {


        int sum = IntStream.iterate(0, n -> n + 3)
                .limit(50)
                .filter(x -> x % 2 == 0)
                .sum();
        System.out.println(sum);
    }
}
