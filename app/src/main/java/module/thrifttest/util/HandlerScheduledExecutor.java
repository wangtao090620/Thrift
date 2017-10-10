package main.java.module.thrifttest.util;

import android.os.Handler;
import android.os.Looper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HandlerScheduledExecutor {

    final String mName;
    final ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    final ScheduledExecutorService mExecutorService;
    final Handler mDefaultHandler = new Handler(Looper.getMainLooper());

    final Logger log;

    public HandlerScheduledExecutor(String name) {
        this(name, 1);
    }

    public HandlerScheduledExecutor(String name, int coreThreadPoolSize) {
        mName = name;
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(coreThreadPoolSize, ConcurrentUtil.newThreadFactory(mName));
        mExecutorService = new FinalizableDelegatedScheduledExecutorService(mScheduledThreadPoolExecutor);
        log = LoggerFactory.getLogger(mName);
    }

    public <T> Future<?> submit(final Task<T> task, final Handler handler) {
        if (task == null)
            return null;

        try {
            return mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        postStart(handler, task);
                        logPreExecute(task);
                        final long start = System.currentTimeMillis();
                        T t = task.call();
                        logPostExecute(task, System.currentTimeMillis() - start);
                        postResult(handler, task, t);
                    } catch (Throwable e) {
                        logException(task, e);
                        postError(handler, task, e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            postError(handler, task, e);
            return null;
        }
    }

    public <T> void submit(Task<T> task) {
        submit(task, mDefaultHandler);
    }

    public void submit(final Runnable r) {
        if (r == null)
            return;

        submit(new Task<Object>(r.getClass().getName()) {
            @Override
            public Object call() throws Exception {
                r.run();
                return null;
            }
        });
    }

    public <T> void schedule(final Task<T> task, long delay, final Handler handler) {
        if (task == null)
            return;

        try {
            mExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        postStart(handler, task);
                        logPreExecute(task);
                        final long start = System.currentTimeMillis();
                        T t = task.call();
                        logPostExecute(task, System.currentTimeMillis() - start);
                        postResult(handler, task, t);
                    } catch (Exception e) {
                        logException(task, e);
                        postError(handler, task, e);
                    }
                }
            }, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            postError(handler, task, e);
        }
    }

    public <T> void schedule(Task<T> task, long delay) {
        schedule(task, delay, mDefaultHandler);
    }

    public void schedule(final Runnable r, long delay) {
        if (r == null)
            return;

        schedule(new Task<Object>(r.getClass().getName()) {
            @Override
            public Object call() throws Exception {
                r.run();
                return null;
            }
        }, delay, mDefaultHandler);
    }

    public <T> void scheduleAtFixedRate(final Task<T> task, long initDelay, long delay, final Handler handler) {
        if (task == null)
            return;

        try {
            mExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        postStart(handler, task);
                        logPreExecute(task);
                        final long start = System.currentTimeMillis();
                        T t = task.call();
                        logPostExecute(task, System.currentTimeMillis() - start);
                        postResult(handler, task, t);
                    } catch (Exception e) {
                        logException(task, e);
                        postError(handler, task, e);
                    }
                }
            }, initDelay, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            postError(handler, task, e);
        }
    }

    public <T> void scheduleAtFixedRate(Task<T> task, long initDelay, long delay) {
        scheduleAtFixedRate(task, initDelay, delay, mDefaultHandler);
    }

    public void scheduleAtFixedRate(final Runnable r, long initDelay, long delay) {
        if (r == null)
            return;

        scheduleAtFixedRate(new Task<Object>(r.getClass().getName()) {
            @Override
            public Object call() throws Exception {
                r.run();
                return null;
            }
        }, initDelay, delay, mDefaultHandler);
    }

    public <T> void scheduleWithFixedDelay(final Task<T> task, long initDelay, long delay, final Handler handler) {
        if (task == null)
            return;

        try {
            mExecutorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        postStart(handler, task);
                        logPreExecute(task);
                        final long start = System.currentTimeMillis();
                        T t = task.call();
                        logPostExecute(task, System.currentTimeMillis() - start);
                        postResult(handler, task, t);
                    } catch (Exception e) {
                        logException(task, e);
                        postError(handler, task, e);
                    }
                }
            }, initDelay, delay, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            postError(handler, task, e);
        }
    }

    public <T> void scheduleWithFixedDelay(Task<T> task, long initDelay, long delay) {
        scheduleWithFixedDelay(task, initDelay, delay, mDefaultHandler);
    }

    public void scheduleWithFixedDelay(final Runnable r, long initDelay, long delay) {
        if (r == null)
            return;

        scheduleWithFixedDelay(new Task<Object>(r.getClass().getName()) {
            @Override
            public Object call() throws Exception {
                r.run();
                return null;
            }
        }, initDelay, delay, mDefaultHandler);
    }

    public void clear() {
        if (mScheduledThreadPoolExecutor == null)
            return;

        BlockingQueue<Runnable> queue = mScheduledThreadPoolExecutor.getQueue();
        if (queue == null)
            return;

        queue.clear();
    }

    public void shutdown() {
        try {
            mExecutorService.shutdown();
        } catch (Exception e) {
        }
    }

    public void shutdownNow() {
        try {
            mExecutorService.shutdownNow();
        } catch (Exception e) {
        }
    }

    public <T> void logPreExecute(Task<T> t) {
        log.trace("PreExecute:" + t.name);
    }

    public <T> void logPostExecute(Task<T> t, long used) {
        log.info("PostExecute:" + t.name + " used:" + used + "ms");
    }

    public <T> void logException(Task<T> t, Throwable e) {
        log.warn("Exception:" + t.name, e);
    }

    private <T> void postStart(Handler handler, final Task<T> task) {
        if (handler == null || task == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                task.onStart();
            }
        });
    }

    private <T> void postResult(Handler handler, final Task<T> task, final T result) {
        if (handler == null || task == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                task.onResult(result);
            }
        });
    }

    private <T> void postError(Handler handler, final Task<T> task, final Throwable e) {
        if (handler == null || task == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                task.onError(e);
            }
        });
    }

    static class DelegatedExecutorService extends AbstractExecutorService {
        private final ExecutorService e;

        DelegatedExecutorService(ExecutorService executor) {
            e = executor;
        }

        public void execute(Runnable command) {
            e.execute(command);
        }

        public void shutdown() {
            e.shutdown();
        }

        public List<Runnable> shutdownNow() {
            return e.shutdownNow();
        }

        public boolean isShutdown() {
            return e.isShutdown();
        }

        public boolean isTerminated() {
            return e.isTerminated();
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return e.awaitTermination(timeout, unit);
        }

        public Future<?> submit(Runnable task) {
            return e.submit(task);
        }

        public <T> Future<T> submit(Callable<T> task) {
            return e.submit(task);
        }

        public <T> Future<T> submit(Runnable task, T result) {
            return e.submit(task, result);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return e.invokeAll(tasks);
        }

        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return e.invokeAll(tasks, timeout, unit);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return e.invokeAny(tasks);
        }

        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return e.invokeAny(tasks, timeout, unit);
        }
    }

    static class FinalizableDelegatedScheduledExecutorService extends DelegatedExecutorService implements ScheduledExecutorService {

        private final ScheduledExecutorService e;

        FinalizableDelegatedScheduledExecutorService(ScheduledExecutorService executor) {
            super(executor);
            e = executor;
        }

        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return e.schedule(command, delay, unit);
        }

        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return e.schedule(callable, delay, unit);
        }

        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return e.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return e.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        protected void finalize() {
            super.shutdown();
        }
    }
}
