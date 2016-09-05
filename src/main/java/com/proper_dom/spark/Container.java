package com.proper_dom.spark;

import com.proper_dom.spark.app.App;
import com.proper_dom.spark.app.CookieJar;
import com.proper_dom.spark.app.FlashAttributes;
import com.proper_dom.spark.app.Renderer;
import com.proper_dom.spark.app.controllers.HomeController;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Container {

    private final MutablePicoContainer container;

    public Container() {
        this.container = new DefaultPicoContainer(new Caching());
    }

    public void registerComponents() {
        container.addComponent(HandlebarsTemplateEngine.class);
        container.addComponent(Renderer.class);
        container.addComponent(CookieJar.class);
        container.addComponent(FlashAttributes.class);
        container.addComponent(HomeController.class);
        container.addComponent(App.class);
    }

    public <T> T getComponent(Class<T> componentType) {
        return container.getComponent(componentType);
    }
}
