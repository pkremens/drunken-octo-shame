package cz.kremilek.synchronization;

/**
 * https://www.javatpoint.com/inter-thread-communication-example
 */
public class InterThreadCommunication {
	public static void main(String args[]) {
		final Customer c = new Customer();
		new Thread(()-> c.withdraw(15000)).start();
		new Thread(()-> c.deposit(15000)).start();
	}

	private static class Customer {
		private int amount = 10000;

		public synchronized void withdraw(int amount) {
			System.out.println(String.format("[%d] going to withdraw %d", this.amount,amount));

			if (this.amount < amount) {
				System.out.println("Less balance; waiting for deposit...");
				try {
					wait();
				} catch (Exception e) {
				}
			}
			this.amount -= amount;
			System.out.println("withdraw completed...");
		}

		public synchronized void deposit(int amount) {
			System.out.println("going to deposit...");
			this.amount += amount;
			System.out.println("deposit completed... ");
			notify();
		}
	}
}

