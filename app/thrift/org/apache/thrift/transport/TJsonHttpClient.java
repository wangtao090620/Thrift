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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * HTTP implementation of the TTransport interface. Used for working with a
 * Thrift web services implementation.
 */
public class TJsonHttpClient extends THttpClient {
    private static final Logger log = LoggerFactory.getLogger("TJsonHttpClient");
    public TJsonHttpClient(String url, boolean post, CryptProvider cryptProvider) throws TTransportException {
        super(url, post, cryptProvider);
    }

    Map<String, String> mGetParams;

    public void addGetParam(String key, String value) {
        if (mGetParams == null)
            mGetParams = new HashMap<String, String>();

        mGetParams.put(key, value);
    }

    public void addGetParam(Map<String, String> params) {
        if (mGetParams == null)
            mGetParams = new HashMap<String, String>();

        if (params != null)
            mGetParams.putAll(params);
    }

    public void flushGet() throws TTransportException {
        // Extract request and reset buffer
        try {
            final byte[] data = requestBuffer_.toByteArray();
            requestBuffer_.reset();

            String str = new String(data, "utf-8");
            JSONObject obj = new JSONObject(str);
            if (mGetParams != null) {
                for (String k : mGetParams.keySet()) {
                    if (obj.has(k))
                        continue;

                    obj.put(k, mGetParams.get(k));
                }
            }
            Iterator<String> iter = obj.keys();
            StringBuilder sb = new StringBuilder(url_);
            if (iter.hasNext()) {
                if (url_ != null && !url_.endsWith("?"))
                    sb.append("?");
                while (iter.hasNext()) {
                    String key = iter.next();
                    String value = obj.getString(key);
                    sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            String urlWithParams = sb.toString();
            log.debug("request config url :"+urlWithParams);
            final URL url = new URL(urlWithParams);
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
        } catch (Exception e) {
            throw new TTransportException(e);
        }
    }
}
