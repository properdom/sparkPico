package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.HomeController;


public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.registerComponents();

        Router router = new Router(container);

        router.get("/home").with(HomeController.class);
        router.post("/home").with(HomeController.class);

        router.before().with(CookieJar.class);
        router.after().with(container);
    }
}
