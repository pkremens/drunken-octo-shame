package cz.kremilek.jdk.jdk11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * https://www.journaldev.com/24601/java-11-features
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class IODemo {
	public static void main(String[] args) throws IOException {
		Path path = Files.writeString(Files.createTempFile("test", ".txt"), "This was posted on JD");
		System.out.println(path);
		String s = Files.readString(path);
		System.out.println(s); //This was posted on JD
	}
}
