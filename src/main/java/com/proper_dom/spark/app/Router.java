package com.proper_dom.spark.app;

import spark.Spark;

class Router {

    private enum Method {
        get, post, before, after
    }

    private Method method;
    private String path;

    static Router get(String path) {
        return new Router(Method.get, path);
    }
    static Router post(String path) {
        return new Router(Method.post, path);
    }
    static Router before() {
        return new Router(Method.before, null);
    }
    static Router after() {
        return new Router(Method.after, null);
    }

    private Router(Method method, String path) {
        this.method = method;
        this.path = path;
    }

    void with(Class<? extends Handler> componentType, Container container) {
        if (method == Method.get) {
            Spark.get(path, (request, response) -> container.getComponent(componentType).get(request, response));
        }

        if (method == Method.post) {
            Spark.post(path, (request, response) -> container.getComponent(componentType).post(request, response));
        }

        if (method == Method.before) {
            Spark.before((request, response) -> container.getComponent(componentType).before(request, response));
        }

        if (method == Method.after) {
            Spark.after((request, response) -> container.getComponent(componentType).after(request, response));
        }
    }

    void with(Handler handler) {
        if (method == Method.get) {
            Spark.get(path, handler::get);
        }

        if (method == Method.post) {
            Spark.post(path, handler::post);
        }

        if (method == Method.before) {
            Spark.before(handler::before);
        }

        if (method == Method.after) {
            Spark.after(handler::after);
        }
    }

    static Container using(Container container) {
        return container;
    }
}
