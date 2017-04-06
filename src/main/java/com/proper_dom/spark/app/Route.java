package com.proper_dom.spark.app;

import spark.Spark;

public class Route {
    private Container container;
    private Class<? extends Handler> handlerClass;
    private Handler handlerImplementation;

    public Route(Container container, Class<? extends Handler> handlerClass) {
        this.container = container;
        this.handlerClass = handlerClass;
    }

    public Route(Container container, Handler handlerImplementation) {
        this.container = container;
        this.handlerImplementation = handlerImplementation;
    }

    public void get(String path) {
        Spark.get(path, (request, response) -> getHandler().get(request, response));
    }

    public void post(String path) {
        Spark.post(path, (request, response) -> getHandler().post(request, response));
    }

    public void before() {
        Spark.before((request, response) -> getHandler().before(request, response));
    }

    public void after() {
        Spark.after((request, response) -> getHandler().after(request, response));
    }

    private Handler getHandler() {
        if (handlerImplementation != null) {
            return handlerImplementation;
        } else {
            return container.getComponent(handlerClass);
        }
    }
}
