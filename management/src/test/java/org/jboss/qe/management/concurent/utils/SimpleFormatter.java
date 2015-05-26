package org.jboss.qe.management.concurent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Petr Kremensky pkremens@redhat.com on 4/29/15.
 */
public class SimpleFormatter extends Formatter {

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
    private boolean showSourceClass = false;

    public void setShowSourceClass(boolean showSourceClass) {
        this.showSourceClass = showSourceClass;
    }

    public synchronized String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(record.getMillis()))).append(" - ");
        if (showSourceClass) {
            builder.append("[").append(record.getSourceClassName()).append(".");
            builder.append(record.getSourceMethodName()).append("] ");
        } else {
            builder.append("[").append(record.getLoggerName());
            builder.append(".").append(record.getSourceMethodName()).append("] ");
        }
        builder.append("[").append(record.getLevel()).append("] ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }
}
