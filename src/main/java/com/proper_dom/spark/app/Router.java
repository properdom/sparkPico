package com.proper_dom.spark.app;

public class Router {

    private Container container;

    public Router(Container container) {
        this.container = container;
        register(container);
    }

    public void register(Class<? extends Handler> handler) throws IllegalAccessException, InstantiationException {
        handler.newInstance().register(new Route(container, handler));
    }

    public void register(Handler handler) {
        handler.register(new Route(container, handler));
    }
}
