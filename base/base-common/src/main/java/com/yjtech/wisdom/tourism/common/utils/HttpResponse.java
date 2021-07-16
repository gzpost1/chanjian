package com.yjtech.wisdom.tourism.common.utils;

import java.util.Map;

public class HttpResponse {
    private int status;
    private String desc;
    private Map<String, String> header;
    private String body;

    public HttpResponse(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDesc() {
        return this.desc;
    }

    protected void setStatus(int status) {
        this.status = status;
    }

    protected void setDesc(String desc) {
        this.desc = desc;
    }

    public void release() {
        if (this.header != null) {
            this.header.clear();
        }

        this.desc = null;
        this.body = null;
    }

}