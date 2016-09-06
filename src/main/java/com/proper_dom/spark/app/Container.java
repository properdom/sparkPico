package com.proper_dom.spark.app;

import com.proper_dom.spark.app.controllers.HomeController;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Container implements Handler {

    private final MutablePicoContainer appContainer;
    private MutablePicoContainer requestContainer;

    public Container() {
        this.appContainer = new DefaultPicoContainer(new Caching());
    }

    public void init() {
        registerApplicationScopedComponents();
    }

    public void registerApplicationScopedComponents() {
        appContainer.addComponent(HandlebarsTemplateEngine.class);
        appContainer.addComponent(Renderer.class);
    }

    private void registerRequestScopedComponents() {
        requestContainer.addComponent(CookieJar.class);
        requestContainer.addComponent(FlashAttributes.class);
        requestContainer.addComponent(HomeController.class);
    }

    public <T> T getComponent(Class<T> componentType) {
        initRequestScopeContainer();
        return requestContainer.getComponent(componentType);
    }

    public void clearRequestScopedContainer() {
        appContainer.removeChildContainer(requestContainer);
        requestContainer = null;
    }

    private void initRequestScopeContainer() {
        if(requestContainer == null) {
            this.requestContainer = new DefaultPicoContainer(new Caching(), appContainer);
            registerRequestScopedComponents();
        }
    }

    @Override
    public void before(Request request, Response response) {
        clearRequestScopedContainer();
        initRequestScopeContainer();
        requestContainer.addComponent(request);
        requestContainer.addComponent(response);
    }
}
