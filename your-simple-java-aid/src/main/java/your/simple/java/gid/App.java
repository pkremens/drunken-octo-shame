package your.simple.java.gid;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("");
        int mb = 1024 * 1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println("Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        System.out.println("Free Memory:"
                + runtime.freeMemory() / mb);

        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);

        //Get the JDK architecture
        System.out.println("JVM Bit size: " + System.getProperty("sun.arch.data.model"));

	System.out.println("user.home propertry default value: " + System.getProperty("user.home"));
	System.setProperty("user.home", "/home/pkremens/devel");
	System.out.println("user.home propertry after change: " + System.getProperty("user.home"));
    }
}
