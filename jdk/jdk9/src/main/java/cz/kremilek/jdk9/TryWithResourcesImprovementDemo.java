package cz.kremilek.jdk9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class TryWithResourcesImprovementDemo {

	public static void main(String[] args) throws IOException {
		File source = new File("src/main/java/cz/kremilek/jdk9/TryWithResourcesImprovementDemo.java");
		testARM_Before_Java9(source);
		System.out.println();
		testARM_Java9(source);
	}

	/**
	 * Java SE 7 example
	 * <p>
	 * We know, Java SE 7 has introduced a new exception handling construct: Try-With-Resources to manage resources
	 * automatically. The main goal of this new statement is “Automatic Better Resource Management”.
	 *
	 * @throws IOException
	 */
	private static void testARM_Before_Java9(File file) throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader(file));
		try (BufferedReader reader2 = reader1) {
			System.out.println(reader2.readLine());
		}
	}

	/**
	 * Java 9 example
	 * <p>
	 * Java SE 9 is going to provide some improvements to this statement to avoid some more verbosity and improve some
	 * Readability.
	 *
	 * @throws IOException
	 */
	private static void testARM_Java9(File file) throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader(file));
		try (reader1) {
			System.out.println(reader1.readLine());
		}
	}

}
