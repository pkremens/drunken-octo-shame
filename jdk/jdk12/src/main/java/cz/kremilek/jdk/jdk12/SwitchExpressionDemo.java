package cz.kremilek.jdk.jdk12;

/**
 * https://www.journaldev.com/28666/java-12-features
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class SwitchExpressionDemo {

	public static void main(String[] args) {
		classicSwitch("M");
	}

	private static void classicSwitch(String day) {
		String result = "";
		switch (day) {
			case "M":
			case "W":
			case "F": {
				result = "MWF";
				break;
			}
			case "T":
			case "TH":
			case "S": {
				result = "TTS";
				break;
			}
		}

		System.out.println("Old Switch Result:");
		System.out.println(result);
	}

	/**
	 * Just a preview, the feature is fully supported since java 14
	 */
//	private static void newSwitch(String day) {
//		String result = switch (day) {
//			case "M", "W", "F" -> "MWF";
//			case "T", "TH", "S" -> "TTS";
//			default -> {
//				if(day.isEmpty())
//					break "Please insert a valid day.";
//				else
//					break "Looks like a Sunday.";
//			}
//
//		};
//
//		System.out.println(result);
//	}
}
