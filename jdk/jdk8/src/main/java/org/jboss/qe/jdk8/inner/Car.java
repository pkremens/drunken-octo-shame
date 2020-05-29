package org.jboss.qe.jdk8.inner;

/**
 * Member inner class
 * <p>
 * Beginning Java 8 Language Features
 *
 * @author Kishori Sharan
 */
public class Car {
    // A member variable for the Car class
    private int year;

    // A member inner class named Tire
    public class Tire {
        // A member variable for the Tire class
        private double radius;

        // Constructor for the Tire class
        public Tire(double radius) {
            this.radius = radius;
        }

        // A member method for the Tire class
        public double getRadius() {
            return radius;
        }
    } // Member inner class declaration ends here

    // A constructor for the Car class
    public Car(int year) {
        this.year = year;
    }

    // A member method for the Car class
    public int getYear() {
        return year;
    }
}

