package cz.kremilek.jdk9;

/**
 * https://www.journaldev.com/13121/java-9-features-with-examples#process-api
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class ProcessApiImprovementsDemo {
	public static void main(String[] args) {

		ProcessHandle currentProcess = ProcessHandle.current();
		System.out.println("Current Process Id: = " + currentProcess.pid());
		System.out.println("Current Process Info: = " + currentProcess.info().toString());

		currentProcess.children().forEach(processHandle -> {
			System.out.println(processHandle.info().toString());
		});
	}
}
