package com.proper_dom.spark.app;

import spark.Request;
import spark.Response;

public class CookieJar implements Handler {
    private Request request;
    private Response response;

    public String get(String name) {
        return request.cookie(name);
    }

    public void add(String name, String value) {
        response.cookie(name, value);
    }

    public void remove(String name) {
        if(request.cookie(name) != null) {
            response.removeCookie(name);
        }
    }

    public void before(Request request, Response response) {
        this.request = request;
        this.response = response;
    }
}
