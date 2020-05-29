package cz.kremilek.jdk10;

import java.util.List;

/**
 * https://www.journaldev.com/20395/java-10-features
 * <p>
 * Limited only to Local Variable with initializer
 * Indexes of enhanced for loop or indexes
 * Local declared in for loop
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class VarDemo {
	public static void main(String[] args) {

		var numbers = List.of(1, 2, 3, 4, 5); // inferred value ArrayList<String>

		// Index of Enhanced For Loop
		for (var number : numbers) {
			System.out.println(number);
		}
		System.out.println();
		// Local variable declared in a loop
		for (var i = 0; i < numbers.size(); i++) {
			System.out.println(numbers.get(i));
		}

		var a = 5;
		var b = 7;
		var c = a + b;
		a = b + c;
		System.out.println("a=" + a);
	}
}
