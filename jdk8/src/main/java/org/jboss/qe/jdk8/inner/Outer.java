package org.jboss.qe.jdk8.inner;

/**
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
public class Outer {
    private int test = 1;

    public class Inner {
        // Members of the Inner class go here

        public class InnerNested {
            // no limitations, cannot be static here
            // inner classes has full access to enclosing class members
            private void fullAccess() {
                test = 2;
            }
        }
    }

    public static class Nested {
        // Members of the Nested class go here

        public static class NestedNested {
        }

        public class NestedInner {
        }
    }

    // Other members of the Outer class go here
}
