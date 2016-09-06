package com.proper_dom.spark.app;

import spark.Spark;

public class Router {

    private Container container;

    public Router(Container container) {
        this.container = container;
    }

    public Route get(String path) {
        return new Route(Method.get, path, container);
    }
    public Route post(String path) {
        return new Route(Method.post, path, container);
    }
    public Route before() {
        return new Route(Method.before, null, container);
    }
    public Route after() {
        return new Route(Method.after, null, container);
    }

    public enum Method {
        get, post, before, after
    }

    static class Route {

        private Method method;
        private String path;
        private Container container;

        public Route(Method method, String path, Container container) {
            this.method = method;
            this.path = path;
            this.container = container;
        }

        public void with(Class<? extends Handler> componentType) {
            if (method == Method.get) {
                Spark.get(path, (request, response) -> container.getComponent(componentType).get(request, response));
            }

            if (method == Method.post) {
                Spark.post(path, (request, response) -> container.getComponent(componentType).post(request, response));
            }

            if (method == Method.before) {
                Spark.before((request, response) -> container.getComponent(componentType).before(request, response));
            }

            if (method == Method.after) {
                Spark.after((request, response) -> container.getComponent(componentType).after(request, response));
            }
        }

        public void with(Handler handler) {
            if (method == Method.get) {
                Spark.get(path, handler::get);
            }

            if (method == Method.post) {
                Spark.post(path, handler::post);
            }

            if (method == Method.before) {
                Spark.before(handler::before);
            }

            if (method == Method.after) {
                Spark.after(handler::after);
            }
        }
    }
}
