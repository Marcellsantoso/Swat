package com.imb.libs.http;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by marcelsantoso on 5/30/15.
 */
public abstract class HTTPAsyncTask
        extends AsyncTask<String, Void, Response> {
    private boolean httpsEnabled     = false;
    private boolean isMultipart      = false;
    private boolean isCache          = true;
    private boolean isEnableSSLCheck = true;
    private URL url;
    private String method = GET;
    private Fragment fragment;
    private             LinkedHashMap<String, String>            params               = new LinkedHashMap<String, String>();
    private             ArrayList<LinkedHashMap<String, String>> fileParams           =
            new ArrayList<LinkedHashMap<String, String>>();
    private             LinkedHashMap<String, byte[]>            bytesParams          = new LinkedHashMap<String, byte[]>();
    private             HashMap<String, String>                  mHeaderParams        = new HashMap<String, String>();
    public static final String                                   RESULTS              = "results";
    public static final String                                   KEY                  = "key";
    public static final String                                   NAME                 = "name";
    public static final String                                   FILEPATH             = "filepath";
    public static final String                                   MESSAGE              = "message";
    public static final String                                   MIME                 = "mime";
    public static final String                                   GET                  = "get";
    public static final String                                   POST                 = "post";
    public static final String                                   MIME_JPEG            = "image/JPEG";
    public static final String                                   MIME_PNG             = "image/PNG";
    public static final String                                   MIME_CSV             = "text/csv";
    public static final int                                      TIMEOUT              = 15000;
    public static final int                                      STATUS_SUCCESS       = 200;
    public static final int                                      STATUS_BAD_REQUEST   = 400;
    public static final int                                      STATUS_NOT_FOUND     = 404;
    public static final int                                      STATUS_TIMEOUT       = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
    public static final int                                      STATUS_NO_DATA       = HttpURLConnection.HTTP_NO_CONTENT;
    public static final int                                      STATUS_NO_CONNECTION = HttpURLConnection.HTTP_NOT_ACCEPTABLE;

    protected abstract void onPreExecute();

    protected abstract void onPostExecute(Response response);

    /**
     * Get the URL
     *
     * @return the URL being used for the end point
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set the URL to be used to connect to the end point
     *
     * @param url , the url to be used
     */
    public HTTPAsyncTask setUrl(String url) {

        try {
            this.url = new URL(url);
            if (url.startsWith("https")) {
                this.httpsEnabled = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public HTTPAsyncTask setGetParams(String key, EditText edt) {
        return setGetParams(key, edt.getText().toString());
    }

    public HTTPAsyncTask setGetParams(String key, String value) {
        if (key != null && value != null && key.trim().length() > 0 &&
                value.trim().length() > 0) {
            String currentUrl = url.toString();
            if (currentUrl.contains("?") &&
                    currentUrl.indexOf("?") <= currentUrl.length()) {
                try {

                    currentUrl += "&" + key + "=" + escapeUrlParam(value);
                    this.url = new URL(currentUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    currentUrl += "?" + key + "=" + escapeUrlParam(value);
                    this.url = new URL(currentUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }

    /**
     * Set the header key value pair
     *
     * @param key
     * @param value
     */
    public HTTPAsyncTask setHeader(String key, String value) {
        mHeaderParams.put(key, value);
        return this;
    }

    public String escapeUrlParam(String param) {
        param = param.replace("%", "%25")
                     .replace("$", "%24")
                     .replace("`", "%60")
                     .replace("<", "%3C")
                     .replace(">", "%3E")
                     .replace("=", "%3D")
                     .replace("'", "%27")
                     .replace("/", "%2F")
                     .replace(":", "%3A")
                     .replace("+", "%2B")
                     .replace("\"", "%22")
                     .replace(" ", "%20")
                     .replace("(", "%28")
                     .replace(")", "%29")
                     .replace("&", "%26")
                     .replace("?", "	%3F");
        return param;
    }

    public HTTPAsyncTask setGetParams(String key, int value) {
        String val = String.valueOf(value);
        return setGetParams(key, val);
    }

    public String getMethod() {
        return method;
    }

    public HTTPAsyncTask setMethod(String method) {
        this.method = method;
        return this;
    }

    public boolean isHttpsEnabled() {
        return httpsEnabled;
    }

    public HTTPAsyncTask setHttpsEnabled(boolean httpsEnabled) {
        this.httpsEnabled = httpsEnabled;
        return this;
    }

    public boolean isEnableSSLCheck() {
        return isEnableSSLCheck;
    }

    public void setEnableSSLCheck(boolean isDisableSSLCheck) {
        this.isEnableSSLCheck = isDisableSSLCheck;
    }

    public HTTPAsyncTask setPostParams(String key, EditText edt) {
        return setPostParams(key, edt.getText().toString());
    }

    public HTTPAsyncTask setPostParams(String key, String value) {
        if (key == null || key.trim().length() <= 0 || value == null ||
                value.trim().length() <= 0) {
            return this;
        }
        this.params.put(key, value);
        this.setMethod(POST);
        return this;
    }

    public HTTPAsyncTask setPostParams(String key, double value) {
        String d = String.valueOf(value);
        this.setPostParams(key, d);

        return this;
    }

    public HTTPAsyncTask setPostParams(String key, int value) {
        String d = String.valueOf(value);
        this.setPostParams(key, d);

        return this;
    }

    public HTTPAsyncTask setImageParams(String key, String absPath) {
        this.setFileParams(key, absPath, MIME_JPEG);

        return this;
    }

    public HTTPAsyncTask setCSVParams(String key, String path) {
        this.setFileParams(key, path, MIME_CSV);

        return this;
    }

    public HTTPAsyncTask setCache(boolean isCache) {
        this.isCache = isCache;

        return this;
    }

    public HTTPAsyncTask setFileParams(String key, String path, String mime) {
        if (path.length() <= 0 || key.trim().length() <= 0) {
            return this;
        }
        this.isMultipart = true;
        String[]                      q    = path.split("/");
        int                           idx  = q.length - 1;
        LinkedHashMap<String, String> file = new LinkedHashMap<String, String>();
        file.put(KEY, key);
        file.put(NAME, q[idx]);
        file.put(FILEPATH, path);
        file.put(MIME, mime);
        this.fileParams.add(file);

        return this;
    }

    public HTTPAsyncTask setByteParams(String key, byte[] bytes) {
        if (key.trim().length() <= 0 || bytes == null || bytes.length <= 0) {
            return this;
        }
        this.isMultipart = true;
        this.bytesParams.put(key, bytes);

        return this;
    }

    public HTTPAsyncTask setFragmentAsync(Fragment fragment) {
        this.fragment = fragment;

        return this;
    }

    public HTTPAsyncTask execute() {
        super.execute();

        return this;
    }

    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} this
     * has been created to aid testing on a local box, not for use on production.
     */
    /*private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                        // Not implemented
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                        // Not implemented
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Get header
     *
     * @param con
     * @param header
     *
     * @return
     */
    public String[] getHeaders(HttpURLConnection con, String header) {
        List<String> values = new ArrayList<String>();
        int          idx    = (con.getHeaderFieldKey(0) == null) ? 1 : 0;
        while (true) {
            String key = con.getHeaderFieldKey(idx);
            if (key == null)
                break;
            if (header.equalsIgnoreCase(key))
                values.add(con.getHeaderField(idx));
            ++idx;
        }
        return values.toArray(new String[values.size()]);
    }

    @Override
    protected Response doInBackground(String... urls) {
        // init
        HttpURLConnection conn        = null;
        InputStream       in          = null;
        int               http_status = 0;
        http_status = STATUS_BAD_REQUEST;
        String   responseString = null;
        Response response       = null;
        int      maxBufferSize  = 2 * 1024 * 1024;
        try {
            //            if (!isEnableSSLCheck)
            //                // Trust all incoming certificates
            //                disableSSLCertificateChecking();

            if (httpsEnabled) {
                conn = (HttpsURLConnection) url.openConnection();
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            conn.setUseCaches(false);
            Set<Map.Entry<String, String>> header = mHeaderParams.entrySet();
            for (Map.Entry<String, String> entry : header) {
                String key = entry.getKey();
                String value = entry.getValue();
                conn.setRequestProperty(key, value);
            }

            // Check if the request should be cached in the network level
            if (!isCache) {
                conn.addRequestProperty("Cache-Control", "no-cache");
            }
            if (this.method.equalsIgnoreCase(POST)) {
                // post data to server
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                if (!isMultipart) {
                    String paramsStr = "";
                    for (String key : params.keySet()) {
                        paramsStr += key + "=" +
                                URLEncoder.encode(params.get(key), "utf-8") +
                                "&";
                    }
                    paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
                    conn.setFixedLengthStreamingMode(
                            paramsStr.getBytes().length);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type",
                                            "application/x-www-form-urlencoded");
                    PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.print(paramsStr);
                    out.close();
                } else {
                    String twoHyphens = "--";
                    String boundary = "*****" +
                            Long.toString(System.currentTimeMillis()) + "*****";
                    String lineEnd = "\r\n";
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type",
                                            "multipart/form-data; boundary="
                                                    + boundary);

                    DataOutputStream outputStream = new DataOutputStream(
                            conn.getOutputStream());
                    for (LinkedHashMap<String, String> map : fileParams) {
                        int bytesRead, bytesAvailable, bufferSize;

                        outputStream.writeBytes(
                                twoHyphens + boundary + lineEnd);
                        outputStream.writeBytes(
                                "Content-Disposition: form-data; name=\""
                                        + map.get(KEY) + "\"; filename=\""
                                        + map.get(NAME) + "\"");
                        outputStream.writeBytes(lineEnd);
                        outputStream
                                .writeBytes("Content-Type: " + map.get(MIME) +
                                                    lineEnd);
                        outputStream.writeBytes(
                                "Content-Transfer-Encoding: binary" + lineEnd);
                        outputStream.writeBytes(lineEnd);

                        // Log.d("value", map.get(Keys.KEY)
                        // +":"+map.get(Keys.NAME)+":"+map.get(Keys.FILEPATH));
                        File file = new File(map.get(FILEPATH));
                        FileInputStream fileInputStream = new FileInputStream(
                                file);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        byte[] buffer = new byte[bufferSize];

                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        while (bytesRead > 0) {
                            outputStream.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable,
                                                  maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                                             bufferSize);
                        }

                        outputStream.writeBytes(lineEnd);

                        fileInputStream.close();
                    }

                    for (String key : this.bytesParams.keySet()) {
                        outputStream.writeBytes(
                                twoHyphens + boundary + lineEnd);
                        outputStream.writeBytes(
                                "Content-Disposition: form-data; name=\"" + key
                                        + "\"; filename=\"uploads.JPG\"");
                        outputStream.writeBytes(lineEnd);
                        outputStream.writeBytes(
                                "Content-Type: image/jpg" + lineEnd);
                        outputStream.writeBytes(
                                "Content-Transfer-Encoding: binary" + lineEnd);
                        outputStream.writeBytes(lineEnd);

                        outputStream.write(bytesParams.get(key));
                        Log.d("value", key + ":" + params.get(key));
                        outputStream.writeBytes(lineEnd);

                    }

                    for (String key : params.keySet()) {
                        outputStream.writeBytes(
                                twoHyphens + boundary + lineEnd);
                        outputStream.writeBytes(
                                "Content-Disposition: form-data; name=\"" + key
                                        + "\"");
                        outputStream.writeBytes(lineEnd);
                        outputStream.writeBytes(
                                "Content-Type: text/plain" + lineEnd);
                        outputStream.writeBytes(lineEnd);
                        outputStream.writeBytes(params.get(key));
                        Log.d("value", key + ":" + params.get(key));
                        outputStream.writeBytes(lineEnd);

                    }

                    outputStream.writeBytes(
                            twoHyphens + boundary + twoHyphens + lineEnd);
                    outputStream.flush();
                    outputStream.close();
                }

            }

            http_status = conn.getResponseCode();

            if (http_status == STATUS_SUCCESS) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }

            if (in != null) {
                // read input from server
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(in));

                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                responseString = sb.toString().replace("\\'", "'");
                response = new Response(http_status, responseString);
                response.setHeaderContent(conn.getHeaderFields());
            }
        } catch (SocketTimeoutException | EOFException e) {
            e.printStackTrace();
            response = new Response(STATUS_TIMEOUT, "");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = new Response(STATUS_NO_CONNECTION, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            response = new Response(HttpURLConnection.HTTP_INTERNAL_ERROR, "");
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        if (this.fragment != null) {
            if (!this.fragment.isAdded())
                this.cancel(true);
        }

        return response;

    }
}

