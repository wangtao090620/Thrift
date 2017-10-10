package main.java.module.thrifttest.util;

import java.util.concurrent.Callable;

public abstract class Task<T> implements Callable<T> {

    public final String name;

    public Task() {
        this.name = getClass().getName();
    }

    public Task(String n) {
        this.name = n;
    }

    public void onStart() {
    }

    public void onResult(T t) {
    }

    public void onError(Throwable e) {
    }
}
