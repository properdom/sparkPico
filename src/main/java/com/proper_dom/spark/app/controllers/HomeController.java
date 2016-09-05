package com.proper_dom.spark.app.controllers;

import com.proper_dom.spark.app.FlashAttributes;
import com.proper_dom.spark.app.Renderer;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class HomeController implements Controller {

    private Renderer renderer;
    private FlashAttributes flashAttributes;

    public HomeController(Renderer renderer, FlashAttributes flashAttributes) {
        this.renderer = renderer;
        this.flashAttributes = flashAttributes;
    }

    @Override
    public void setupRoutes() {
        get("/home", this::handleGet);
        post("/home", this::handlePost);
    }

    public String handleGet(Request request, Response response) {
        flashAttributes.load();
        Map<String, String> model = new HashMap<>();
        model.put("name", flashAttributes.get("name"));
        return renderer.render(model, "home.hbs");
    }

    public Response handlePost(Request request, Response response) {
        flashAttributes.put("name", request.queryParams("name"));
        flashAttributes.store();
        response.redirect("/home", 303);
        return response;
    }
}
