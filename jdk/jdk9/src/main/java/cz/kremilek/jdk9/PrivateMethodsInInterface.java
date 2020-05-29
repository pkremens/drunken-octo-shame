package cz.kremilek.jdk9;

/**
 * https://www.journaldev.com/13121/java-9-features-with-examples#private-methods
 *
 * Tohle je docela divne, default VS private
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public interface PrivateMethodsInInterface {

	private Long createCardID(){
		return 1L;
	}

	private static void displayCardDetails(){
		System.out.println("displayCardDetails()");
	}

	default Long publicMethod() {
		displayCardDetails();
		return createCardID();
	}
}
