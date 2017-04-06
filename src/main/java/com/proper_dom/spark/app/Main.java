package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.HomeController;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Container container = new Container();

        container.registerApplicationScopedComponent(HandlebarsTemplateEngine.class);
        container.registerApplicationScopedComponent(Renderer.class);

        container.registerRequestScopedComponent(CookieJar.class);
        container.registerRequestScopedComponent(FlashAttributes.class);
        container.registerRequestScopedComponent(HomeController.class);

        Router router = new Router(container);

        router.register(HomeController.class);
    }
}
