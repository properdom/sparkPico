package com.proper_dom.spark.app.controllers;

import com.proper_dom.spark.app.*;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController implements Handler {

    private Renderer renderer;
    private FlashAttributes flashAttributes;

    public HomeController() {
        this.renderer = null;
        this.flashAttributes = null;
    }

    public HomeController(Renderer renderer, FlashAttributes flashAttributes) {
        this.renderer = renderer;
        this.flashAttributes = flashAttributes;
    }

    public String get(Request request, Response response) {
        flashAttributes.load();
        Map<String, String> model = new HashMap<>();
        model.put("name", flashAttributes.get("name"));
        return renderer.render(model, "home.hbs");
    }

    public Response post(Request request, Response response) {
        flashAttributes.put("name", request.queryParams("name"));
        flashAttributes.store();
        response.redirect("/home", 303);
        return response;
    }

    @Override
    public void register(Route route) {
        route.get("/home");
        route.post("/home");
    }
}
