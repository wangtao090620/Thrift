package main.java.module.thrifttest.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentUtil {

    public static ThreadFactory newThreadFactory(final String name, final int priority, final boolean daemon) {
        return new ThreadFactory() {
            private final AtomicInteger no = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "" + name + "-" + no.getAndIncrement());
                if (t.isDaemon() != daemon)
                    t.setDaemon(daemon);
                if (t.getPriority() != priority)
                    t.setPriority(priority);
                return t;
            }
        };
    }

    public static ThreadFactory newThreadFactory(final String name, final int priority) {
        return newThreadFactory(name, priority, false);
    }

    public static ThreadFactory newThreadFactory(final String name) {
        return newThreadFactory(name, Thread.NORM_PRIORITY, false);
    }
}
