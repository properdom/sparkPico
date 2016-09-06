package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.HomeController;

import static com.proper_dom.spark.app.Router.*;


public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.registerComponents();

        get("/home").with(HomeController.class, using(container));
        post("/home").with(HomeController.class, using(container));

        before().with(CookieJar.class, using(container));
        after().with(container);
    }
}
