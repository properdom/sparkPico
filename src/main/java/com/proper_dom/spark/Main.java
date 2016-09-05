package com.proper_dom.spark;

import com.proper_dom.spark.app.App;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.registerComponents();
        container.getComponent(App.class).init();
    }
}
