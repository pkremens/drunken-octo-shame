package cz.kremilek.synchronization;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
public class DeamonThread {
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Hello from deamon.");
			}
		});
		thread1.setDaemon(true);
		thread1.start();
		Thread.sleep(2000L);
	}
}
