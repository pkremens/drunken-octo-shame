package cz.kremilek.jdk9;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class PrivateMethodsInInterfaceDemo {
	public static void main(String[] args) {
		PrivateMethodsInInterface demo = new PrivateMethodsInInterfaceImpl();

		System.out.println(demo.publicMethod());

	}
}
