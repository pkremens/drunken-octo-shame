package org.jboss.qe.jdk8.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
public class GenerateStream {
    public static void main(String[] args) {
        Stream.of("Hello");
        Stream.of("one", "two").count();

        String[] names = {"Ken", "Jeff", "Chris", "Ellen"};

        // Creates a stream of four strings in the names array
        Stream<String> stream = Stream.of(names);

        /*
        The Stream.of() method creates a stream whose elements are of reference type. If you want to create a
stream of primitive values from an array of primitive type, you need to use the Arrays.stream() method that will be
explained shorty.
         */


        // Gets a stream builder
        Stream.Builder<String> builder = Stream.builder();
        builder.accept("one");
        builder.add("two").add("three").add("four").build().forEach(
                System.out::println
        );

        Stream<String> emptyStream = Stream.empty();
        IntStream.range(1, 5).count();

        // Creates a stream of natural numbers
        Stream<Long> naturalNumbers = Stream.iterate(1L, n -> n + 1);

        // Creates a stream of odd natural numbers
        Stream<Long> oddNaturalNumbers = Stream.iterate(1L, n -> n + 2);
    }

}
