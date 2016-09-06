package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.HomeController;


public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.init();

        Router router = new Router(container);

        router.before().with(container);
        router.get("/home").with(HomeController.class);
        router.post("/home").with(HomeController.class);
    }
}
