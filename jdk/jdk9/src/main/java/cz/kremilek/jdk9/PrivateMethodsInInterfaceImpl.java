package cz.kremilek.jdk9;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class PrivateMethodsInInterfaceImpl implements PrivateMethodsInInterface {

	private Long createCardID(){
		return 1L;
	}
}
