package org.jboss.qe.management.concurent.measure;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class Measure {
    private final MBeanServerConnection connection;
    private final JMXConnector jmxConnector;
    private Long heapSize = 0L;
    private Long maxHeapSize = 0L;
    private Long nonHeapSize = 0L;
    private int threadCount = 0;
    private int peakThreadCount = 0;

    public Measure() throws Exception {
        this("localhost", 9999);
    }

    public Measure(String host, int port) throws Exception {
        this(System.getProperty("jmx.service.url", "service:jmx:remoting-jmx://" + host + ":" + port));
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

    public String measure() throws Exception {
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
            memoryPool = (CompositeDataSupport) connection.getAttribute(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
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

    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("*******************************************************************").append("\n");
        sb.append("Current Time:      ").append(Calendar.getInstance().getTime()).append("\n");
        sb.append("Uptime:            ").append(getUptime()).append("\n");
        sb.append("Heap memory:       ").append((heapSize / 1000 / 1000) + " MB").append("\n");
        sb.append("Max Heap memory:   ").append((maxHeapSize / 1000 / 1000) + " MB").append("\n");
        sb.append("NonHeap memory:    ").append((nonHeapSize / 1000 / 1000) + " MB").append("\n");
        sb.append("Thread count:      ").append(threadCount).append("\n");
        sb.append("Peak Thread Count: ").append(peakThreadCount).append("\n");
        sb.append("*******************************************************************");
        return sb.toString();
    }

    private String getUptime() {
        long millis = ManagementFactory.getRuntimeMXBean().getUptime();
        Date date = new Date(millis - 3600 * 1000);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
/*
        System.out.println(String.format("%d hr, %d min, %d sec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)
                )));
                */
        return formatter.format(date);
    }
}