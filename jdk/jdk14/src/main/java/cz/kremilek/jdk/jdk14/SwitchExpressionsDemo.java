package cz.kremilek.jdk.jdk14;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class SwitchExpressionsDemo {
	public static void main(String[] args) {
		String day = "T";

		String result = switch (day) {
			case "M", "W", "F" -> "MWF";
			case "T", "TH", "S" -> "TTS";
			default -> {
				if(day.isEmpty())
					yield "Please insert a valid day.";
				else
					yield "Looks like a Sunday.";
			}

		};
		System.out.println(result);
	}
}
