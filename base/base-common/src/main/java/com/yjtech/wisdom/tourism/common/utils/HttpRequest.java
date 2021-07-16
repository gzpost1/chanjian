package com.yjtech.wisdom.tourism.common.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String target;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String body;
    private int timeout;

    public HttpRequest(String target) {
        this.target = target;
        this.params = new HashMap();
        this.headers = new HashMap();
        this.setTimeout(30000);
    }

    public String getTarget() {
        return this.target;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public void addParams(Map<String, String> srcp) {
        this.params.putAll(srcp);
    }

    public Map<String, String> getExtHeaders() {
        return this.headers;
    }

    public void addExtHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void release() {
        this.params.clear();
        this.headers.clear();
        this.body = null;
    }

}