package main.java.module.thrifttest.giphy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.giphy.protocol.GiphyService;
import com.giphy.protocol.SearchRequest;
import com.giphy.protocol.SearchResponse;
import com.proto.protocol.Code;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TJsonHttpClient;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import main.java.module.thrifttest.MainApp;
import main.java.module.thrifttest.util.HandlerScheduledExecutor;
import main.java.module.thrifttest.util.Initializable;
import main.java.module.thrifttest.util.Task;

public class GiphyServiceApi implements GiphyService.Iface, Initializable {

    static final Logger log = LoggerFactory.getLogger("GiphyServiceApi");

    static final String HEADER_API_VERSION = "ApiVersion";

    static final String API_VERSION = "1.0";

    public static abstract class Callback<Response> {
        public void onStart() {
        }

        public abstract void onSuccess(Response res, Map<String, List<String>> headers);

        public void onError(int code, String msg, Map<String, List<String>> headers, Throwable t) {
        }

        public void onCancelled() {
        }
    }

    public static <Response> void doCallbackOnStart(final Callback<Response> callback, Handler handler) {
        if (callback == null || handler == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onStart();
            }
        });
    }

    public static <Response> void doCallbackOnSuccess(final Callback<Response> callback, Handler handler, final Response res, final Map<String, List<String>> headers) {
        if (callback == null || handler == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(res, headers);
            }
        });
    }

    public static <Response> void doCallbackOnError(final Callback<Response> callback, Handler handler, final int code, final String msg, final Map<String, List<String>> headers,
                                                    final Throwable t) {
        if (callback == null || handler == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code, msg, headers, t);
            }
        });
    }

    public static <Response> void doCallbackOnCancelled(final Callback<Response> callback, Handler handler) {
        if (callback == null || handler == null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onCancelled();
            }
        });
    }

    public static class CallbackWrapper<Response> extends Callback<Response> {

        final Callback<Response> mCallback;

        public CallbackWrapper(Callback<Response> callback) {
            mCallback = callback;
        }

        @Override
        public void onStart() {
            if (mCallback != null)
                mCallback.onStart();
        }

        protected void onPreSuccess(Response res, Map<String, List<String>> headers) {
        }

        @Override
        public void onSuccess(Response res, Map<String, List<String>> headers) {
            onPreSuccess(res, headers);

            if (mCallback != null)
                mCallback.onSuccess(res, headers);
        }

        protected void onPreError(Throwable t, String msg, Map<String, List<String>> headers) {
        }

        @Override
        public void onError(int code, String msg, Map<String, List<String>> headers, Throwable t) {
            onPreError(t, msg, headers);

            if (mCallback != null)
                mCallback.onError(code, msg, headers, t);
        }

        @Override
        public void onCancelled() {
            if (mCallback != null)
                mCallback.onCancelled();
        }
    }

    public static interface Interceptor {

        public interface Callback {
            public void run(Map<String, Object> params);
        }

        public void intercept(Callback onOk, Callback onFail, Callback onCancel);

        public static class Params {
            public static final String RESPONSE = "response";
            public static final String CODE = "code";
            public static final String MSG = "msg";
            public static final String HEADERS = "headers";
            public static final String THROWABLE = "throwable";

            @SuppressWarnings("unchecked")
            public static <T> T get(Map<String, Object> params, String key, Class<T> clazz, T defValue) {
                if (params == null)
                    return defValue;

                Object value = params.get(key);
                if (value == null || !clazz.isInstance(value))
                    return defValue;

                return (T) value;
            }

            public static Map<String, Object> makeParams(Object res, int code, String msg, Map<String, List<String>> headers, Throwable t) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put(RESPONSE, res);
                result.put(CODE, code);
                result.put(MSG, msg);
                result.put(HEADERS, headers);
                result.put(THROWABLE, t);
                return result;
            }

            public static Object getResponse(Map<String, Object> params, Object defValue) {
                return get(params, RESPONSE, Object.class, defValue);
            }

            public static int getCode(Map<String, Object> params, int defValue) {
                return get(params, CODE, Integer.class, defValue);
            }

            public static String getMsg(Map<String, Object> params, String defValue) {
                return get(params, MSG, String.class, defValue);
            }

            @SuppressWarnings("unchecked")
            public static Map<String, List<String>> getHeaders(Map<String, Object> params, Map<String, List<String>> defValue) {
                return get(params, HEADERS, Map.class, defValue);
            }

            public static Throwable getThrowable(Map<String, Object> params, Throwable defValue) {
                return get(params, THROWABLE, Throwable.class, defValue);
            }
        }
    }

    public static interface OnResponseListener {
        void onResponse(Object response);
    }

    protected Context mContext;

    protected final Handler mHandler;

    protected final HandlerScheduledExecutor mImeServiceApiExecutor = new HandlerScheduledExecutor("GiphyService", 4);

    protected final ThreadLocal<THttpClient> mHttpClient = new ThreadLocal<THttpClient>();

    protected final Hashtable<String, String> mRequestHeaders = new Hashtable<String, String>();

    protected final ThreadLocal<Hashtable<String, String>> mTempRequestHeaders = new ThreadLocal<Hashtable<String, String>>();

    protected THttpClient.CryptProvider mCryptProvider = null;

    protected Interceptor mInterceptor = null;

    protected final List<OnResponseListener> mOnResponseListeners = new ArrayList<OnResponseListener>();

    protected String mUrl;

    protected int mConnectTimeout = 0;

    protected int mReadTimeout = 0;

    public GiphyServiceApi() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void init(Context ctx) {
        mContext = ctx;
    }

    @Override
    public String getName() {
        return "GiphyServiceApi";
    }

    public static GiphyServiceApi shared() {
        return MainApp.shared().get(GiphyServiceApi.class);
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setConnectTimeout(int timeout) {
        if (timeout < 0)
            throw new IllegalArgumentException("timeout must >= 0");

        mConnectTimeout = timeout;
    }

    public void setReadTimeout(int timeout) {
        if (timeout < 0)
            throw new IllegalArgumentException("timeout must >= 0");

        mReadTimeout = timeout;
    }

    public HandlerScheduledExecutor getExecutor() {
        return mImeServiceApiExecutor;
    }

    private GiphyService.Iface createImeServiceIface(String url) throws TTransportException {
        THttpClient tc = new THttpClient(url, true, mCryptProvider);
        if (mConnectTimeout > 0)
            tc.setConnectTimeout(mConnectTimeout);
        if (mReadTimeout > 0)
            tc.setReadTimeout(mReadTimeout);
        synchronized (mRequestHeaders) {
            Hashtable<String, String> headers = new Hashtable<String, String>(mRequestHeaders);
            headers.put(HEADER_API_VERSION, API_VERSION);

            // add temp headers
            Hashtable<String, String> tempHeaders = mTempRequestHeaders.get();
            if (tempHeaders != null) {
                headers.putAll(tempHeaders);
                mTempRequestHeaders.remove();
            }
            tc.setCustomHeaders(headers);
        }
        TBinaryProtocol prot = new TBinaryProtocol(tc);
        GiphyService.Iface i = new GiphyService.Client(prot);
        mHttpClient.set(tc);
        return i;
    }

    private GiphyService.Iface createImeServiceIface() throws TTransportException {
        return createImeServiceIface(mUrl);
    }

    private GiphyService.Iface createImeServiceIfaceJson(String url, Map<String, String> params, THttpClient.CryptProvider provider) throws TTransportException {
        TJsonHttpClient tc = new TJsonHttpClient(url, false, provider);
        tc.addGetParam(params);
        if (mConnectTimeout > 0)
            tc.setConnectTimeout(mConnectTimeout);
        if (mReadTimeout > 0)
            tc.setReadTimeout(mReadTimeout);
        synchronized (mRequestHeaders) {
            Hashtable<String, String> headers = new Hashtable<String, String>(mRequestHeaders);
            headers.put(HEADER_API_VERSION, API_VERSION);

            // add temp headers
            Hashtable<String, String> tempHeaders = mTempRequestHeaders.get();
            if (tempHeaders != null) {
                headers.putAll(tempHeaders);
                mTempRequestHeaders.remove();
            }
            tc.setCustomHeaders(headers);
        }
        GiphyService.Iface i = new GiphyService.JsonClient(tc);
        mHttpClient.set(tc);
        return i;
    }

    private THttpClient createGetHttpClient(String url, THttpClient.CryptProvider cryptProvider) throws TTransportException {
        THttpClient tc = new THttpClient(url, false, cryptProvider);
        if (mConnectTimeout > 0)
            tc.setConnectTimeout(mConnectTimeout);
        if (mReadTimeout > 0)
            tc.setReadTimeout(mReadTimeout);
        synchronized (mRequestHeaders) {
            Hashtable<String, String> headers = new Hashtable<String, String>(mRequestHeaders);
            headers.put(HEADER_API_VERSION, API_VERSION);

            // add temp headers
            Hashtable<String, String> tempHeaders = mTempRequestHeaders.get();
            if (tempHeaders != null) {
                headers.putAll(tempHeaders);
                mTempRequestHeaders.remove();
            }
            tc.setCustomHeaders(headers);
        }
        mHttpClient.set(tc);
        return tc;
    }

    public void clearRequestHeaders() {
        synchronized (mRequestHeaders) {
            mRequestHeaders.clear();
        }
    }

    public void setRequestHeader(String key, String value) {
        synchronized (mRequestHeaders) {
            mRequestHeaders.put(key, value);
        }
    }

    public void removeRequestHeader(String key) {
        synchronized (mRequestHeaders) {
            mRequestHeaders.remove(key);
        }
    }

    public void setTempRequestHeader(String key, String value) {
        synchronized (mRequestHeaders) {
            Hashtable<String, String> temp = mTempRequestHeaders.get();
            if (temp == null) {
                temp = new Hashtable<String, String>();
                mTempRequestHeaders.set(temp);
            }

            temp.put(key, value);
        }
    }

    private Map<String, List<String>> pollResponseHeaders() {
        try {
            THttpClient tc = mHttpClient.get();
            if (tc == null)
                return null;

            return tc.getResponseHeaders();
        } finally {
            mHttpClient.remove();
        }
    }

    public void setInterceptor(Interceptor interceptor) {
        mInterceptor = interceptor;
    }

    public void addOnResponseListener(OnResponseListener listener) {
        if (mOnResponseListeners.contains(listener))
            return;

        mOnResponseListeners.add(listener);
    }

    public void setCryptProvider(THttpClient.CryptProvider cryptProvider) {
        mCryptProvider = cryptProvider;
    }

    private <T> T onResponse(T t) {
        if (mOnResponseListeners != null) {
            for (OnResponseListener l : mOnResponseListeners) {
                try {
                    l.onResponse(t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        return t;
    }

    public static String parseCodeMsg(int code) {
        if (code == Code.NetworkError.getValue())
            return "";

        return "";
    }

    public static int parseErrorCode(Throwable t) {
        if (t instanceof ApiException) {
            return ((ApiException) t).code;
        }

        if (t instanceof OutOfMemoryError) {
            return Code.ServerError.getValue();
        }

        return Code.Unknown.getValue();
    }

    public static String parseErrorMessage(Throwable t) {
        if (t instanceof Exception) {
            return ((Exception) t).getMessage();
        }

        return t != null ? t.getMessage() : "";
    }

    public static boolean checkNetAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return (activeNetInfo == null) ? false : true;
        } catch (Exception e) {
            return true;
        }
    }

    public void checkNetwork() throws TException {
        if (!checkNetAvailable(mContext))
            throw new ApiException("", Code.NetworkError.getValue());
    }

    public <Response> Future<?> submit(final Task<Response> task, final Callback<Response> callback, Handler handler, boolean useInterceptor) {
        final Handler h = handler != null ? handler : mHandler;
        if (h != null && callback != null) {
            h.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null)
                        callback.onStart();
                }
            });
        }

        if (mInterceptor == null || !useInterceptor) {
            return mImeServiceApiExecutor.submit(task, h);
        } else {
            mInterceptor.intercept(new Interceptor.Callback() {
                @Override
                public void run(Map<String, Object> params) {
                    mImeServiceApiExecutor.submit(task, h);
                }
            }, new Interceptor.Callback() {
                @Override
                public void run(Map<String, Object> params) {
                    doCallbackOnError(callback, h, Interceptor.Params.getCode(params, Code.Unknown.getValue()), Interceptor.Params.getMsg(params, ""),
                            Interceptor.Params.getHeaders(params, null), Interceptor.Params.getThrowable(params, null));
                }
            }, new Interceptor.Callback() {
                @Override
                public void run(Map<String, Object> params) {
                    doCallbackOnCancelled(callback, h);
                }
            });
            return null;
        }
    }

    public interface Cancellable {
        void cancel();
    }

    public <Response> Cancellable submit(final Task<Response> task, final Callback<Response> callback, Handler handler) {
        final Future<?> future = submit(task, callback, handler, true);
        return new Cancellable() {
            @Override
            public void cancel() {
                if (future != null)
                    future.cancel(true);

                if (task instanceof TaskImpl)
                    ((TaskImpl) task).cancel();
            }
        };
    }

    public static abstract class TaskImpl<Response> extends Task<Response> implements Cancellable {

        final Callback<Response> mCallback;

        boolean mCancelled = false;

        volatile Map<String, List<String>> mResponseHeaders;

        public TaskImpl(Callback<Response> callback) {
            mCallback = callback;
        }

        protected final void setResponseHeaders(Map<String, List<String>> headers) {
            mResponseHeaders = headers;
        }

        protected void fetchResponseHeaders(GiphyServiceApi api) {
            setResponseHeaders(api.pollResponseHeaders());
        }

        public void cancel() {
            if (mCancelled)
                return;

            synchronized (this) {
                mCancelled = true;
            }
        }

        @Override
        public void onResult(Response t) {
            synchronized (this) {
                if (mCancelled) {
                    if (mCallback != null)
                        mCallback.onCancelled();
                    return;
                }
            }

            if (mCallback != null)
                mCallback.onSuccess(t, mResponseHeaders);
        }

        @Override
        public void onError(Throwable e) {
            synchronized (this) {
                if (mCancelled) {
                    if (mCallback != null)
                        mCallback.onCancelled();
                    return;
                }
            }

            if (mCallback != null)
                mCallback.onError(parseErrorCode(e), parseErrorMessage(e), mResponseHeaders, e);
        }
    }

    @Override
    public SearchResponse search(SearchRequest req) throws TException {
        checkNetwork();
        try {
            GiphyService.Iface i = createImeServiceIfaceJson(mUrl, null, null);
            SearchResponse res = i.search(req);
            return onResponse(res);
        } catch (Throwable e) {
            throw e;
        }
    }

    public Cancellable search(final SearchRequest req, final Callback<SearchResponse> callback, final Handler handler) {
        return submit(new TaskImpl<SearchResponse>(callback) {
            @Override
            public SearchResponse call() throws Exception {
                try {
                    return search(req);
                } finally {
                    fetchResponseHeaders(GiphyServiceApi.this);
                }
            }
        }, callback, handler);
    }

    public Cancellable search(final SearchRequest req, final Callback<SearchResponse> callback) {
        return search(req, callback, null);
    }
}
