package cz.kremilek.jdk.jdk11;

/**
 * Java 11 String Class New Methods
 * <p>
 * There are many new methods added in String class in Java 11 release.
 * <p>
 * isBlank() – returns true if the string is empty or contains only white space codepoints, otherwise false.
 * lines() – returns a stream of lines extracted from this string, separated by line terminators.
 * strip(), stripLeading(), stripTrailing() – for stripping leading and trailing white spaces from the string.
 * repeat() – returns a string whose value is the concatenation of this string repeated given number of times.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class StringTest {
	private static final String hellos = "Hi\nHello\nHowdy\nNazdar\nAhoj";


	public static void main(String[] args) {
		////
		hellos.lines().filter(s -> s.startsWith("H")).forEach(StringTest::print);
		System.out.println(hellos);
		////

		////
		for (String line : hellos.split(System.lineSeparator())) { // "\n"
			if (line.startsWith("H")) {
				print(line);
			}
		}
		////
	}

	private static void print(String text) {
		System.out.println(text);
	}
}
