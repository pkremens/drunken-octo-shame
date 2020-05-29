package cz.kremilek.jdk.jdk12;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;

/**
 * https://www.journaldev.com/28666/java-12-features
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TeeingCollectorDemo {
	public static void main(String[] args) {
		double mean = Stream.of(1, 2, 3, 4, 5)
				.collect(Collectors.teeing(
						summingDouble(i -> i),
						counting(),
						(sum, n) -> sum / n));

		System.out.println(mean);
	}
}
