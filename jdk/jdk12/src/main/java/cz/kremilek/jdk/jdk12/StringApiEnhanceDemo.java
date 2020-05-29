package cz.kremilek.jdk.jdk12;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class StringApiEnhanceDemo {

	public static void main(String[] args) {
//		indentDemo();
//		transformDemo();
		describeConstableDemo();
	}

	// ehm
	// "The Constants API methods don’t have much usage for normal development related tasks."
	private static void describeConstableDemo() {
		String so = "Hello";
		Optional os = so.describeConstable();
		System.out.println(os);
		System.out.println(os.get());
	}

	private static void transformDemo() {
		String s = "Hi,Hello,Howdy";
		List strList = s.transform(s1 -> {
			return Arrays.asList(s1.split(","));
		});
		System.out.println(strList);
	}

	private static void indentDemo() {
		String str = "*****\n  Hi\n  \tHello Pankaj\rHow are you?\n*****";

		System.out.println(str.indent(0));
		System.out.println(str.indent(3));
		System.out.println(str.indent(-3));
	}
}
