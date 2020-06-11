package cz.kremilek.synchronization;

/**
 * https://www.javatpoint.com/deadlock-in-java
 */
public class DeadlockExample {
	public static void main(String[] args) throws InterruptedException {
		final String resource1 = "resource1";
		final String resource2 = "resource2";
		// t1 tries to lock resource1 then resource2
		Thread t1 = new Thread(() -> {
			synchronized (resource1) {
				System.out.println(Thread.currentThread().getName() + ": locked " + resource1);

				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}

				synchronized (resource2) {
					System.out.println(Thread.currentThread().getName() + ": locked " + resource2);
				}
			}
		});

		// t2 tries to lock resource2 then resource1
		Thread t2 = new Thread(() -> {
			synchronized (resource2) {
				System.out.println(Thread.currentThread().getName() + ": locked " + resource2);

				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}

				synchronized (resource1) {
					System.out.println(Thread.currentThread().getName() + ": locked " + resource1);
				}
			}
		});


		t1.start();
		t2.start();
	}
}
