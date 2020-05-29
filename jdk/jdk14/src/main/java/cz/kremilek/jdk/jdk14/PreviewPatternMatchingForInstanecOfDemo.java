package cz.kremilek.jdk.jdk14;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class PreviewPatternMatchingForInstanecOfDemo {

	public static void main(String[] args) {
		EncapsulatedString obj = new EncapsulatedString("Hello");

		// Before Java 14:
		if (obj instanceof EncapsulatedString) {
			EncapsulatedString message = (EncapsulatedString) obj;
			System.out.println(message.getMessage());
		}

		// Java 14 Onwards:
		if (obj instanceof EncapsulatedString message2) {
			System.out.println(message2.getMessage());
		}
	}
}
