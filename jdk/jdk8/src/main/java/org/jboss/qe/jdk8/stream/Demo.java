package org.jboss.qe.jdk8.stream;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * @author Petr Kremensky pkremens@redhat.com on 07/03/2016
 */
public class Demo {
    /*
     * - Streams have no storage.
     * - Streams can represent a sequence of infinite elements.
     * - The design of streams is based on internal iteration.
     * - Streams are designed to be processed in parallel with no additional work from the developers.
     * - Streams are designed to support functional programming.
     * - Streams support lazy operations.
     * - Streams can be ordered or unordered.
     * - Streams cannot be reused.
     */

    public static void main(String[] args) {
        parallelStream();
    }

    /**
     * Well, this doesn't seem to be the best candidate for the parallel run :), guess that sum aggregation makes this
     * wait for the slowest thread, differences without the sum operation are negligible.
     */
    private static void parallelStream() {
        Set<Integer> numbers = new HashSet<>();
        IntStream.range(1, 50000).forEach(numbers::add);

        long start;

        start = System.nanoTime();
        numbers.parallelStream()
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .reduce(0, Integer::sum);
        System.out.println("Parallel stream execution took:\n - " + (System.nanoTime() - start));

        start = System.nanoTime();
        numbers.stream()
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .reduce(0, Integer::sum);
        System.out.println("Non-parallel stream execution took:\n - " + (System.nanoTime() - start));

        start = System.nanoTime();
        numbers.parallelStream()
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .reduce(0, Integer::sum);
        System.out.println("Parallel stream execution took:\n - " + (System.nanoTime() - start));

        start = System.nanoTime();
        numbers.stream()
                .filter(n -> n % 3 == 0)
                .map(n -> n * 2)
                .reduce(0, Integer::sum);
        System.out.println("Non-parallel stream execution took:\n - " + (System.nanoTime() - start));
    }
}
