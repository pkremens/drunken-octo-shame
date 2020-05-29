package cz.kremilek.jdk.jdk14;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class AuthorRecordDemo {
	public static void main(String[] args) {
		List<AuthorRecord> authorList = new ArrayList<>(List.of(new AuthorRecord(1, "Foo", "Bar")));
		authorList.add(new AuthorRecord(2, "Bar", "Foo"));

		authorList.forEach(author -> System.out.println(author.name()));
	}
}
