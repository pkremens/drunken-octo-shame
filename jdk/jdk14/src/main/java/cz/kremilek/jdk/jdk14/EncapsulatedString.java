package cz.kremilek.jdk.jdk14;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class EncapsulatedString {
	private String message;

	public EncapsulatedString() {
	}

	public EncapsulatedString(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return "Encapsulated: " + message;
	}
}
