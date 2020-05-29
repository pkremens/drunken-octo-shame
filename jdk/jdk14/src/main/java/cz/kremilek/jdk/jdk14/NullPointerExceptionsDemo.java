package cz.kremilek.jdk.jdk14;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class NullPointerExceptionsDemo {

	/**
	 * Well, I do not see any difference here :) https://www.journaldev.com/37273/java-14-features
	 */
	public static void main(String[] args) {
		System.out.println(new EncapsulatedString().getMessage().length());
	}
}
