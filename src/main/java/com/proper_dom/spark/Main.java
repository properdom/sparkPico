package com.proper_dom.spark;

public class Main {

    public static void main(String[] args) {
        Container container = new Container();
        container.registerComponents();
        container.getComponent(App.class).init();
    }
}
