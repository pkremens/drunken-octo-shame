package cz.kremilek.jdk9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.journaldev.com/13121/java-9-features-with-examples#factory-methods-immutable
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class FactoryMethodsDemo {
	final static Logger log = LoggerFactory.getLogger(FactoryMethodsDemo.class);

	public static void main(String[] args) {
		List immutableList = List.of();

		// throws Exception in thread "main" java.lang.UnsupportedOperationException
		log.info("List.of(); is immutable");
		try {
			immutableList.add("test");
		} catch (UnsupportedOperationException uoe) {
			log.info(uoe.getClass().getName());
		}

		log.info("Stream.of(); is mutable");
		immutableList = Stream.of().collect(Collectors.toList());
		immutableList.add("test2");
	}
}
