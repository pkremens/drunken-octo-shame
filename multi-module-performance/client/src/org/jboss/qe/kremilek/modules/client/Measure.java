package org.jboss.qe.kremilek.modules.client;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Class designed to measure EAP server resources during cli tests. Heap-size, Non-heap-size, Thread-count and
 * Peak-thread-count attributes are monitored.
 *
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class Measure {
    private final MBeanServerConnection connection;
    private final JMXConnector jmxConnector;
    private Long heapSize = 0L;
    private Long oldHeap = 0L;
    private Long maxHeapSize = 0L;
    private Long nonHeapSize = 0L;
    private Long oldNonHeap = 0L;
    private int threadCount = 0;
    private int oldThreads = 0;
    private int peakThreadCount = 0;
    private int oldPeak = 0;
    private final long startTime = System.currentTimeMillis();

    public Measure() throws Exception {
        this("localhost", 9990);
    }

    public Measure(String host, int port) throws Exception {
        this(System.getProperty("jmx.service.url", "service:jmx:remote+http://" + host + ":" + port));
    }

    public Measure(String urlString) throws Exception {
        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        jmxConnector = JMXConnectorFactory.connect(serviceURL, null);
        connection = jmxConnector.getMBeanServerConnection();
        resetPeakCounter();
        maxHeapSize = getMaxHeapSize(); // measure maximum heap size only at the startup
        measure();
    }


    public void destroy() throws Exception {
        jmxConnector.close();
    }

    /**
     * @return Latest measurement report (0 values => failed to measure).
     */
    public String measure() throws Exception {
        forceGC();
        threadCount = getThreadCount();
        peakThreadCount = getPeakThreadCount();
        heapSize = getHeapSize();
        nonHeapSize = getNonHeapSize();
        return this.getReport();
    }

    private void forceGC() throws Exception {
        connection.invoke(new ObjectName("java.lang:type=Memory"), "gc", null, null);
    }

    private void resetPeakCounter() throws Exception {
        connection.invoke(new ObjectName("java.lang:type=Threading"), "resetPeakThreadCount", null, null);
    }

    private Long getHeapSize() {
        CompositeDataSupport heap = getMemory("HeapMemoryUsage");
        return heap == null ? 0L : (Long) heap.get("used");
    }

    private Long getMaxHeapSize() {
        CompositeDataSupport heap = getMemory("HeapMemoryUsage");
        return heap == null ? 0L : (Long) heap.get("max");
    }

    private Long getNonHeapSize() {
        CompositeDataSupport heap = getMemory("NonHeapMemoryUsage");
        return heap == null ? 0L : (Long) heap.get("used");
    }

    private CompositeDataSupport getMemory(String pool) {
        CompositeDataSupport memoryPool = null;
        try {
            memoryPool = (CompositeDataSupport) connection.getAttribute(new ObjectName("java.lang:type=Memory"), pool);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return memoryPool;
    }

    private int getThreadCount() {
        return getThreads("ThreadCount");
    }

    private int getPeakThreadCount() {
        return getThreads("PeakThreadCount");
    }

    private int getThreads(String attribute) {
        int threadCount = 0;
        try {
            threadCount = (Integer) connection.getAttribute(new ObjectName("java.lang:type=Threading"), attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return threadCount;
    }

    /**
     * Get the latest measured values.
     *
     * @return Report of measured values.
     */
    public String getReport() {
        String diff;
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("*******************************************************************").append("\n");
        sb.append("Current Time:      ").append(Calendar.getInstance().getTime()).append("\n");
        sb.append("Uptime:            ").append(getUptime()).append("\n");
        diff = memoryDiff(oldHeap, heapSize);
        oldHeap = heapSize;
        sb.append("Heap memory:       ").append(heapSize / 1000 / 1000).append(" MB").append(diff).append("\n");
        sb.append("Max Heap memory:   ").append(maxHeapSize / 1000 / 1000).append(" MB").append("\n");
        diff = memoryDiff(oldNonHeap, nonHeapSize);
        oldNonHeap = nonHeapSize;
        sb.append("NonHeap memory:    ").append(nonHeapSize / 1000 / 1000).append(" MB").append(diff).append("\n");
        diff = threadsDiff(oldThreads, threadCount);
        oldThreads = threadCount;
        sb.append("Thread count:      ").append(threadCount).append(diff).append("\n");
        diff = threadsDiff(oldPeak, peakThreadCount);
        oldPeak = peakThreadCount;
        sb.append("Peak Thread Count: ").append(peakThreadCount).append(diff).append("\n");
        sb.append("*******************************************************************");
        return sb.toString();
    }

    private String memoryDiff(long old, long current) {
        long now = (current - old) / 1000000;
        return String.format(" (%s%d)", now < 0 ? "" : "+", now);
    }

    private String threadsDiff(int old, int current) {
        int now = current - old;
        return String.format(" (%s%d)", now < 0 ? "" : "+", now);
    }

    private String getUptime() {
        long millis = System.currentTimeMillis() - startTime;
        return String.format("%d hr, %d min, %d sec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)
                ));
    }
}