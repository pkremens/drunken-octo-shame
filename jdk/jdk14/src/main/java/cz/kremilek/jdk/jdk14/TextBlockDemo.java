package cz.kremilek.jdk.jdk14;

/**
 * https://www.journaldev.com/37273/java-14-features
 * <p>
 * Backslash for displaying nice-looking multiline string blocks.
 * \s is used to consider trailing spaces which are by default ignored by the compiler. It preserves all the spaces present before it.
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TextBlockDemo {
	public static void main(String[] args) {
		System.out.println("---------------------------");
		System.out.println("""
				Did you know \
				Java %d \
				has the most features among\
				all non-LTS versions so far\
				""".formatted(14));
		System.out.println("---------------------------");
		System.out.println("""
				line1
				line2 \s
				line3
				""");
		System.out.println("---------------------------");

		System.out.println("line1\nline2 \nline3\n".indent(2));
		System.out.println("---------------------------");
	}
}
