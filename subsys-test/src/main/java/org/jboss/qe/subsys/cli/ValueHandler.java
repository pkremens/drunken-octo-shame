package org.jboss.qe.subsys.cli;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Petr Kremensky pkremens@redhat.com
 */
class ValueHandler {
    private final AtomicInteger value = new AtomicInteger(0);
    static final ValueHandler INSTANCE = new ValueHandler();

    public int increase() {
        return value.incrementAndGet();
    }

    public int decrease() {
        return value.decrementAndGet();
    }
}
