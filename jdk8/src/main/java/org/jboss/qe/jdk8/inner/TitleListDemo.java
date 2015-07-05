package org.jboss.qe.jdk8.inner;

import java.util.Iterator;

/**
 * Local inner class test.
 * <p>
 * Must be one of:
 * - Implement a public interface
 * - Inherit from another public class and override some of its superclass methods
 * <p>
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
public class TitleListDemo {
    public static void main(String[] args) {
        TitleList tl = new TitleList();

        // Add two titles
        tl.addTitle("Beginning Java 8");
        tl.addTitle("Scripting in Java");

        // Get the iterator
        Iterator iterator = tl.titleIterator();

        // Print all titles using the iterator
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
