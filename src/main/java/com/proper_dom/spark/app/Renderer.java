package com.proper_dom.spark.app;

import spark.ModelAndView;
import spark.TemplateEngine;

public class Renderer {

    private TemplateEngine templateEngine;

    public Renderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String render(Object model, String template) {
        return templateEngine.render(new ModelAndView(model, template));
    }
}
