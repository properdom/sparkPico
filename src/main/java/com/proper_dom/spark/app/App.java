package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.Controller;

import java.util.List;

public class App {
    private List<Controller> controllers;
    private List<Filter> filters;

    public App(List<Controller> controllers, List<Filter> filters) {
        this.controllers = controllers;
        this.filters = filters;
    }

    public void init() {
        controllers.stream().forEach(Controller::setupRoutes);
        filters.stream().forEach(Filter::setupRoutes);
    }
}
