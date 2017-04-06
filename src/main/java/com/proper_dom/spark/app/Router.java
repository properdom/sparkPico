package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.Controller;

import java.lang.reflect.InvocationTargetException;

public class Router {

    private Container container;

    public Router(Container container) {
        this.container = container;
        register(container);
    }

    public void register(Class<? extends Controller> handler) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        handler.getDeclaredConstructor(Renderer.class, FlashAttributes.class)
                .newInstance(null, null)
                .register(new Route(container, handler));
    }

    public void register(Handler handler) {
        handler.register(new Route(container, handler));
    }
}
