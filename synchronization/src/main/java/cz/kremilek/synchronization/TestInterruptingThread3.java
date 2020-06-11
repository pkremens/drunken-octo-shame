package cz.kremilek.synchronization;

class TestInterruptingThread3 extends Thread {

	public void run() {
		boolean interupted;
		for (int i = 1; i <= 500; i++) {
			interupted = Thread.currentThread().isInterrupted();
			System.out.println(i + " interupted: " + interupted);
			if (interupted) {
				break;
			}
		}
	}

	public static void main(String args[]) throws InterruptedException {
		TestInterruptingThread3 t1 = new TestInterruptingThread3();
		t1.start();
		Thread.sleep(1L);
		t1.interrupt();
	}
}