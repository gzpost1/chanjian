package com.yjtech.wisdom.tourism.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * simulate browser to setup http(s) connection send request, receive response.
 * this class is thread unsafe
 *
 * @author liuhong
 */
@Slf4j
public class HttpSession {
    private static final String PREFIX = "--", LINEND = "\r\n";
    private static final String MULTIPART_FROM_DATA = "multipart/form-data";
    private static final String CHARSET = "utf-8";
    private static final Pattern PARSE_COOKIE = Pattern.compile("expires=|path=|domain=");
    private static final Map<String, String> baseHeader = new HashMap<String, String>();

    static {
        baseHeader.put("Accept", "*/*");
        baseHeader.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        baseHeader.put("Accept-Encoding", "gzip,deflate,sdch");
        baseHeader.put("Accept-Language", "zh-CN,zh;q=0.8");
        baseHeader.put("Connection", "keep-alive");
        baseHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
    }

    private static TrustManager defaultTrust = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    };

    private static HostnameVerifier hostVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    };

    private static SSLSocketFactory sslFactory;

    private Map<String, String> cookies;

    private Proxy proxy = null;

    /**
     * create session, no proxy when setup connection
     */
    public HttpSession() {
        this(null);
    }

    /**
     * create session
     *
     * @param proxy format is ip:port, if format is wrong, don't use proxy
     */
    public HttpSession(String proxy) {
        cookies = new HashMap<String, String>();
        if (proxy != null) {
            String[] tmp = proxy.split(":");
            if (tmp.length == 2) {
                this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(tmp[0], Integer.parseInt(tmp[1])));
            }
        }
    }

    public void release() {
        if (cookies != null)
            cookies.clear();
        cookies = null;
        proxy = null;
    }

    public HttpResponse sendGetRequest(HttpRequest request) throws Exception {
        StringBuilder target = new StringBuilder(request.getTarget());
        if (request.getParams().size() > 0) {
            target.append("?");
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                target.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        HttpURLConnection conn = getConnection(target.toString(), request.getTimeout(), request.getExtHeaders());
        try {
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            return makeResponse(conn, true);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public HttpResponse sendDeleteRequest(HttpRequest request) throws Exception {
        StringBuilder target = new StringBuilder(request.getTarget());
        if (request.getParams().size() > 0) {
            target.append("?");
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                target.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        HttpURLConnection conn = getConnection(target.toString(), request.getTimeout(), request.getExtHeaders());
        try {
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            return makeResponse(conn, true);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public Map<String, String> getCookie() {
        return cookies;
    }

    /**
     * the content is request.getParams + request.getBody
     *
     * @param request
     * @return
     * @throws Exception
     */
    public HttpResponse sendPostRequest(HttpRequest request) throws Exception {
        HttpURLConnection conn = getConnection(request.getTarget(), request.getTimeout(), request.getExtHeaders());
        try {
            if (request.getBody() != null)
                conn.setRequestProperty("Content-Length", String.valueOf(request.getBody().getBytes(CHARSET).length));
            else {
                request.setBody("");
                conn.setRequestProperty("Content-Length", "0");
            }

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            out.write(request.getBody().getBytes(CHARSET));
            out.flush();
            return makeResponse(conn, true);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    /**
     * use the "put" method of http
     *
     * @param request
     * @return
     * @throws Exception
     */
    public HttpResponse sendPutRequest(HttpRequest request) throws Exception {
        HttpURLConnection conn = getConnection(request.getTarget(), request.getTimeout(), request.getExtHeaders());
        try {
            if (request.getBody() != null)
                conn.setRequestProperty("Content-Length", String.valueOf(request.getBody().getBytes(CHARSET).length));
            else {
                request.setBody("");
                conn.setRequestProperty("Content-Length", "0");
            }

            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            out.write(request.getBody().getBytes(CHARSET));
            out.flush();
            return makeResponse(conn, true);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    /**
     * http method of patch
     * the content is request.getParams + request.getBody
     *
     * @param request
     * @return
     * @throws Exception
     */
    public HttpResponse sendPatchRequest(HttpRequest request) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch patchReq = new HttpPatch(request.getTarget());
        StringEntity patchBody = new StringEntity(request.getBody(), CHARSET);
        if (request.getExtHeaders().containsKey("Content-Type")) {
            patchBody.setContentType(new BasicHeader("Content-Type", request.getExtHeaders().get("Content-Type")));
        }
        patchReq.setEntity(patchBody);
        generateHeaders(request, patchReq);
        CloseableHttpResponse response = httpClient.execute(patchReq);

        return makeResponse(request.getTarget(), response, true);
    }

    private void generateHeaders(HttpRequest request, HttpPatch patch) {
        if (request.getExtHeaders() != null && request.getExtHeaders().size() > 0) {
            for (Map.Entry<String, String> entry : request.getExtHeaders().entrySet()) {
                patch.addHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
    }

    private HttpURLConnection getConnection(String url, int timeout, Map<String, String> extHeader) throws Exception {
        URL target = new URL(url);
        HttpURLConnection conn;
        if (this.proxy != null)
            conn = (HttpURLConnection) target.openConnection(proxy);
        else
            conn = (HttpURLConnection) target.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection sslConn = (HttpsURLConnection) conn;
            synchronized (defaultTrust) {
                if (sslFactory == null) {
                    SSLContext context = SSLContext.getInstance("SSL", "SunJSSE");
                    context.init(null, new TrustManager[]{defaultTrust}, new java.security.SecureRandom());
                    sslFactory = context.getSocketFactory();
                }
            }
            sslConn.setSSLSocketFactory(sslFactory);
            sslConn.setHostnameVerifier(hostVerifier);
        }
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(timeout);

        for (Map.Entry<String, String> entry : baseHeader.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : extHeader.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        if (cookies.isEmpty() == false) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : cookies.entrySet())
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            conn.setRequestProperty("Cookie", sb.toString());
        }

        return conn;
    }

    private HttpResponse makeResponse(HttpURLConnection conn, boolean withbody) throws Exception {
        int code = conn.getResponseCode();

        //if (code == 200 || code == 302 || code == 304)
        if (code >= 400)
            log.error("access " + conn.getURL() + " meet error:" + conn.getResponseMessage());

        HttpResponse response = new HttpResponse(conn.getResponseCode(), conn.getResponseMessage());

        // dump response header and cookie
        Map<String, String> headers = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
            if (Objects.isNull(entry.getKey()))
                continue;

            if (entry.getKey().equals("Set-Cookie")) {
                saveCookies(entry.getValue());
                log.info("", entry.getValue());
            } else {
                for (String item : entry.getValue())
                    sb.append(item).append(";");

                sb.deleteCharAt(sb.length() - 1);
                headers.put(entry.getKey(), sb.toString());
                sb.setLength(0);
            }
        }
        response.setHeader(headers);

        // read response body from input stream
        if (withbody) {
            StringBuilder body = new StringBuilder();
            getResponseBody(conn, body);
            response.setBody(body.toString());
        }

        return response;
    }

    private HttpResponse makeResponse(String target, CloseableHttpResponse patchResp, boolean withbody) throws Exception {
        int code = patchResp.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(patchResp.getEntity());

        //if (code == 200 || code == 302 || code == 304)
        if (code >= 400)
            log.error("access " + target + " meet error:" + responseBody);

        HttpResponse response = new HttpResponse(code, patchResp.getStatusLine().getReasonPhrase());


        // save cookie
        HeaderElementIterator it = new BasicHeaderElementIterator(
                patchResp.headerIterator("Set-Cookie"));
        List<String> cookie = new ArrayList<>();
        while (it.hasNext()) {
            HeaderElement elem = it.nextElement();
            cookie.add(elem.getName() + "=" + elem.getValue());
            NameValuePair[] params = elem.getParameters();
            for (int i = 0; i < params.length; i++) {
                cookie.add(" " + params[i]);
            }
        }
        if (!cookie.isEmpty()) {
            saveCookies(cookie);
            log.info("", cookie);
        }
        // dump response header and cookie
        Map<String, String> headers = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Header header : patchResp.getAllHeaders()) {
            if (Objects.isNull(header.getName()))
                continue;

            if (!header.getName().equals("Set-Cookie")) {
                headers.put(header.getName(), header.getValue());
                sb.setLength(0);
            }
        }
        response.setHeader(headers);

        // read response body from input stream
        if (withbody) {
            response.setBody(responseBody);
        }

        return response;
    }

    private void saveCookies(List<String> cookieList) {
        int pos = 0;
        for (String cookie : cookieList) {
            String[] items = cookie.split(";");
            for (String item : items) {
                if (PARSE_COOKIE.matcher(item).find())
                    continue;

                pos = item.indexOf("=");
                if (pos > 0) {
                    cookies.put(item.substring(0, pos), item.substring(pos + 1));
                }
            }
        }
    }

    private void getResponseBody(HttpURLConnection conn, StringBuilder body) throws IOException {
        //if (conn.getContentLength() <= 0)
        //    return;

        int len = 0;
        body.setLength(0);
        byte[] buff = new byte[512];
        InputStream in = null;
        if (conn.getResponseCode() < 400) {
            if (conn.getContentEncoding() != null && conn.getContentEncoding().equalsIgnoreCase("gzip"))
                in = new GZIPInputStream(conn.getInputStream(), 1024);
            else
                in = new BufferedInputStream(conn.getInputStream());
        } else {
            try {
                in = new BufferedInputStream(conn.getErrorStream());
            } catch (Exception exp) {
                in = new BufferedInputStream(conn.getInputStream());
            }
        }

        while ((len = in.read(buff)) > 0) {
            body.append(new String(buff, 0, len, CHARSET));
        }
        in.close();
    }



}

