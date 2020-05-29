package cz.kremilek.jdk.jdk12;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * https://www.journaldev.com/28666/java-12-features
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class CompactNumberFormatting {
	public static void main(String[] args) {
		System.out.println("Compact Formatting is:");

		for (Locale locale : List.of(new Locale("cs", "CZ"), new Locale("en", "US"))) {
			printFormatted(locale);
		}

	}

	private static void printFormatted(Locale locale) {
		System.out.println(locale.toLanguageTag());
		NumberFormat upvotes = NumberFormat
				.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
		upvotes.setMaximumFractionDigits(1);

		System.out.println(upvotes.format(2592) + " upvotes");


		NumberFormat upvotes2 = NumberFormat
				.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
		upvotes2.setMaximumFractionDigits(2);
		System.out.println(upvotes2.format(2011) + " upvotes");
		System.out.println();
	}
}
