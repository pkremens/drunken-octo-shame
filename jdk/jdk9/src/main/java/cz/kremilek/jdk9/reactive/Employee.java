package cz.kremilek.jdk9.reactive;

/**
 * https://www.journaldev.com/20723/java-9-reactive-streams
 *
 * @author Petr Kremensky pkremens@redhat.com
 */

public class Employee {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee(int i, String s) {
		this.id = i;
		this.name = s;
	}

	public Employee() {
	}

	@Override
	public String toString() {
		return "[id=" + id + ",name=" + name + "]";
	}
}
