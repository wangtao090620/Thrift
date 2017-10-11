package main.java.module.thrifttest;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

import main.java.module.thrifttest.giphy.GiphyServiceApi;
import main.java.module.thrifttest.proto.ImeServiceApi;
import main.java.module.thrifttest.util.Initializable;
import module.thrifttest.BuildConfig;


public class MainApp extends Application {

    static final String TAG = "MainApp";

    private static MainApp sShared;

    final HashMap<Class<?>, Object> mShareds = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        sShared = this;
        ImeServiceApi imeServiceApi = ImeServiceApi.shared();
        imeServiceApi.setUrl(BuildConfig.GANK_URL);
        GiphyServiceApi.shared().setUrl(BuildConfig.GIPHY_URL);
    }

    public static MainApp shared() {
        return sShared;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        if (clazz == null)
            return null;

        Object old = mShareds.get(clazz);
        if (old != null)
            return (T) old;

        String iName = clazz.getSimpleName();
        try {
            synchronized (mShareds) {
                old = mShareds.get(clazz);
                if (old != null)
                    return (T) old;

                final long start = System.currentTimeMillis();
                final T obj = clazz.newInstance();
                if (obj instanceof Initializable) {
                    final Initializable i = ((Initializable) obj);
                    final String name = i.getName();
                    iName = (name != null ? name : clazz.getSimpleName());
                    i.init(this);
                    Log.i(TAG, iName + ":init used:" + (System.currentTimeMillis() - start) + "ms");
                }
                mShareds.put(clazz, obj);
                return obj;
            }
        } catch (Throwable e) {
            Log.w(TAG, iName + ":init exception", e);
            return null;
        }
    }
}
