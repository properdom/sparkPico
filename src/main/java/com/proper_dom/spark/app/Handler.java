package com.proper_dom.spark.app;

import spark.Request;
import spark.Response;

public interface Handler {
    void register(Route route);

    default String get(Request request, Response response) {
        return null;
    }

    default Response post(Request request, Response response) {
        return response;
    }

    default void before(Request request, Response response) {
        return;
    }

    default void after(Request request, Response response) {
        return;
    }
}
