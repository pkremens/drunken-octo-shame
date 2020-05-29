package cz.kremilek.jdk9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * https://www.tutorialspoint.com/when-to-use-the-delayedexecutor-method-of-completablefuture-in-java-9
 *
 * @author Petr Kremensky pkremens@redhat.com
 */
public class CompletableFutureApiImprovementDemo {

	public static void main(String args[]) {
		CompletableFuture<String> future = new CompletableFuture<>();
		future.completeAsync(() -> {
			try {
				System.out.println("inside future: processing data...");
				return "tutorialspoint.com";
			} catch (Throwable e) {
				return "not detected";
			}
		}, CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS))
				.thenAccept(result -> System.out.println("accept: " + result));
		for (int i = 1; i <= 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("running outside... " + i + " s");
		}
	}
}
