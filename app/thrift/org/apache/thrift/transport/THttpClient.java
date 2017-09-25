/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.thrift.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * HTTP implementation of the TTransport interface. Used for working with a
 * Thrift web services implementation.
 */
public class THttpClient extends TTransport {

    protected String url_ = null;

    protected final ByteArrayOutputStream requestBuffer_ = new ByteArrayOutputStream();

    protected InputStream inputStream_ = null;

    protected int connectTimeout_ = 0;

    protected int readTimeout_ = 0;

    protected Hashtable<String, String> customHeaders_ = null;

    protected Map<String, List<String>> responseHeaders_ = null;

    final boolean mPost;

    final CryptProvider mCryptProvider;

    public THttpClient(String url, boolean post, CryptProvider cryptProvider) throws TTransportException {
        url_ = url;
        mPost = post;
        mCryptProvider = cryptProvider;
    }

    public void setConnectTimeout(int timeout) {
        connectTimeout_ = timeout;
    }

    public void setReadTimeout(int timeout) {
        readTimeout_ = timeout;
    }

    public void setCustomHeaders(Hashtable<String, String> headers) {
        customHeaders_ = headers;
    }

    public void setCustomHeader(String key, String value) {
        if (customHeaders_ == null) {
            customHeaders_ = new Hashtable<String, String>();
        }
        customHeaders_.put(key, value);
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders_;
    }

    public InputStream getInputStream() {
        return inputStream_;
    }

    public void open() {
    }

    public void close() {
        if (null != inputStream_) {
            try {
                inputStream_.close();
            } catch (IOException ioe) {
            }
            inputStream_ = null;
        }
    }

    public boolean isOpen() {
        return true;
    }

    public int read(byte[] buf, int off, int len) throws TTransportException {
        if (inputStream_ == null) {
            throw new TTransportException("Response buffer is empty, no request.");
        }
        try {
            int ret = inputStream_.read(buf, off, len);
            if (ret == -1) {
                throw new TTransportException("No more data available.");
            }
            return ret;
        } catch (IOException iox) {
            throw new TTransportException(iox);
        }
    }

    public void write(byte[] buf, int off, int len) {
        requestBuffer_.write(buf, off, len);
    }

    public static interface CryptProvider {
        public void init(URLConnection conn) throws IOException;

        public OutputStream encrypt(URLConnection conn, OutputStream os) throws IOException;

        public InputStream decrypt(URLConnection conn, InputStream is) throws IOException;

        public void done() throws IOException;
    }

    void init(URLConnection conn) throws IOException {
        if (mCryptProvider == null)
            return;

        mCryptProvider.init(conn);
    }

    OutputStream encrypt(URLConnection conn, OutputStream os) throws IOException {
        if (mCryptProvider == null)
            return os;

        return mCryptProvider.encrypt(conn, os);
    }

    InputStream decrypt(URLConnection conn, InputStream is) throws IOException {
        if (mCryptProvider == null)
            return is;

        return mCryptProvider.decrypt(conn, is);
    }

    void done() throws IOException {
        if (mCryptProvider == null)
            return;

        mCryptProvider.done();
    }

    public void flush() throws TTransportException {
        if (mPost)
            flushPost();
        else
            flushGet();
    }

    public void flushPost() throws TTransportException {
        // Extract request and reset buffer
        final byte[] data = requestBuffer_.toByteArray();
        requestBuffer_.reset();

        try {
            // Create connection object
            final URL url = new URL(url_);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Timeouts, only if explicitly set
            if (connectTimeout_ > 0) {
                connection.setConnectTimeout(connectTimeout_);
            }
            if (readTimeout_ > 0) {
                connection.setReadTimeout(readTimeout_);
            }

            // Make the request
            connection.setDoOutput(true);//使用 URL 连接进行输出
            connection.setDoInput(true);//使用 URL 连接进行输入
            connection.setUseCaches(false);//忽略缓存

            // Make the request
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-stream");
            connection.setRequestProperty("Accept", "application/x-stream");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "Java/Android");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Keep-Alive", "5000");
            connection.setRequestProperty("Http-version", "HTTP/1.1");
            connection.setRequestProperty("Cache-Control", "no-transform");

            if (customHeaders_ != null) {
                for (Enumeration<String> e = customHeaders_.keys(); e.hasMoreElements(); ) {
                    String key = (String) e.nextElement();
                    String value = (String) customHeaders_.get(key);
                    connection.setRequestProperty(key, value);
                }
            }

            init(connection);

            connection.connect();
            final OutputStream os = encrypt(connection, connection.getOutputStream());
            os.write(data);
            os.close();

            responseHeaders_ = connection.getHeaderFields();

            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new TTransportException("HTTP Response code: " + responseCode);
            }

            InputStream is = null;

            // Read the responses
            final String contentEncoding = connection.getContentEncoding();
            final boolean gzipped = contentEncoding != null && contentEncoding.toLowerCase(Locale.US).contains("gzip");
            final InputStream origin = connection.getInputStream();
            if (gzipped) {
                is = new GZIPInputStream(origin);
            } else {
                is = origin;
            }

            inputStream_ = decrypt(connection, is);

            done();

        } catch (IOException iox) {
            throw new TTransportException(iox);
        }
    }

    public void flushGet() throws TTransportException {
        // Extract request and reset buffer
        try {
            // Create connection object
            final URL url = new URL(url_);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Timeouts, only if explicitly set
            if (connectTimeout_ > 0) {
                connection.setConnectTimeout(connectTimeout_);
            }
            if (readTimeout_ > 0) {
                connection.setReadTimeout(readTimeout_);
            }

            // Make the request
            connection.setDoOutput(false);//使用 URL 连接进行输出
            connection.setDoInput(true);//使用 URL 连接进行输入
            connection.setUseCaches(true);//使用缓存

            // Make the request
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-stream");
            connection.setRequestProperty("Accept", "application/x-stream");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("User-Agent", "Java/Android");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Keep-Alive", "5000");
            connection.setRequestProperty("Http-version", "HTTP/1.1");

            if (customHeaders_ != null) {
                for (Enumeration<String> e = customHeaders_.keys(); e.hasMoreElements(); ) {
                    String key = (String) e.nextElement();
                    String value = (String) customHeaders_.get(key);
                    connection.setRequestProperty(key, value);
                }
            }

            init(connection);

            connection.connect();

            responseHeaders_ = connection.getHeaderFields();

            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new TTransportException("HTTP Response code: " + responseCode);
            }

            InputStream is = null;

            // Read the responses
            final String contentEncoding = connection.getContentEncoding();
            final boolean gzipped = contentEncoding != null && contentEncoding.toLowerCase(Locale.US).contains("gzip");
            final InputStream origin = connection.getInputStream();
            if (gzipped) {
                is = new GZIPInputStream(origin);
            } else {
                is = origin;
            }

            inputStream_ = decrypt(connection, is);

            done();

        } catch (IOException iox) {
            throw new TTransportException(iox);
        }
    }
}
