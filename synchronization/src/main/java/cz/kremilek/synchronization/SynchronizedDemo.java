package cz.kremilek.synchronization;

/**
 * https://www.javatpoint.com/synchronization-in-java
 */
public class SynchronizedDemo {
	public static void main(String[] args) throws InterruptedException {
		// https://www.javatpoint.com/synchronization-in-java
//		method();
//		synchronizedMethod();
		https:
//www.javatpoint.com/synchronized-block-example
		synchronizedBlock();
	}

	/*
	Thread-0: 5
	...
	Thread-0: 25
	Thread-1: 10
	Thread-1: 20
	Thread-0: 30
	...
	*/
	private static void method() throws InterruptedException {
		System.out.println("\nmethod not synchronized:");
		Thread thread1 = new Thread(() -> printTable(5, Thread.currentThread().getName()));
		Thread thread2 = new Thread(() -> printTable(10, Thread.currentThread().getName()));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}

	/*
	Thread-2: 5
	...
	Thread-2: 50
	Thread-3: 10
	...
	Thread-3: 100
	 */
	private static void synchronizedMethod() throws InterruptedException {
		System.out.println("\nsynchronized method:");
		Thread thread1 = new Thread(() -> printSynchronizedTable(5, Thread.currentThread().getName()));
		Thread thread2 = new Thread(() -> printSynchronizedTable(10, Thread.currentThread().getName()));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}

	/*
	Thread-0: 50
	Thread-1: 20
	Thread-0: entering synchronized block
	Thread-1: 30
	Thread-0-sync-block: 5
	...
	Thread-0-sync-block: 50
	Thread-1: 40
	Thread-0: leaving synchronized block
	Thread-1: 50
	...
	Thread-1: 100
	Thread-1: entering synchronized block
	Thread-1-sync-block: 10
	...
	 */
	private static void synchronizedBlock() throws InterruptedException {
		System.out.println("\nsynchronized block:");
		Thread thread1 = new Thread(() -> printTableWithSynchdonizedBlock(5, Thread.currentThread().getName()));
		Thread thread2 = new Thread(() -> printTableWithSynchdonizedBlock(10, Thread.currentThread().getName()));
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}

	private static void printTable(int n, String threadName) {
		for (int i = 1; i <= 10; i++) {
			System.out.println(threadName + ": " + n * i);
		}
	}

	private static synchronized void printSynchronizedTable(int n, String threadName) {
		printTable(n, threadName);
	}

	private static void printTableWithSynchdonizedBlock(int n, String threadName) {
		printTable(n, threadName);
		synchronized (SynchronizedDemo.class) {
			System.out.println(threadName + ": entering synchronized block");
			printTable(n, threadName + "-sync-block");
			System.out.println(threadName + ": leaving synchronized block");
		}
	}
}
