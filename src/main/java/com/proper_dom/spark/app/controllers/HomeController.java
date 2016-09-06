package com.proper_dom.spark.app.controllers;

import com.proper_dom.spark.app.Handler;
import com.proper_dom.spark.app.FlashAttributes;
import com.proper_dom.spark.app.Renderer;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController implements Handler {

    private Renderer renderer;
    private FlashAttributes flashAttributes;

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
}
