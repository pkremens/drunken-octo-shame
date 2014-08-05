package org.jboss.qa.checker.model

/**
 * Created with IntelliJ IDEA.
 * User: pkremens
 * Date: 8/5/14
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
class Computer {
    private String name
    private boolean offline
    private boolean temporarilyOffline

    public Computer(Object computer) {
        name = computer.displayName
        offline = (boolean) computer.offline
        temporarilyOffline = (boolean) computer.temporarilyOffline
    }

    /**
     * Return true, if the slave is not broken or disconnected (can be temporarily offline).
     */
    public boolean isOnline() {
        !offline || temporarilyOffline
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Computer computer = (Computer) o

        if (online != computer.online) return false
        if (name != computer.name) return false

        return true
    }

    int hashCode() {
        int result
        result = name.hashCode()
        result = 31 * result + (online ? 1 : 0)
        return result
    }


    @Override
    public java.lang.String toString() {
        return "Computer{" +
                "name='" + name + '\'' +
                ", online=" + online +
                '}';
    }
}