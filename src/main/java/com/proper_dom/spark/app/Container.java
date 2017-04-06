package com.proper_dom.spark.app;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import spark.Request;
import spark.Response;

import java.util.LinkedList;
import java.util.List;

public class Container implements Handler {

    private final MutablePicoContainer appContainer;
    private MutablePicoContainer requestContainer;
    private List<Class> requestScopedComponents;

    public Container() {
        this.appContainer = new DefaultPicoContainer(new Caching());
        this.requestContainer = new DefaultPicoContainer(new Caching(), appContainer);
        this.requestScopedComponents = new LinkedList<>();
    }

    public void registerApplicationScopedComponent(Class applicationScopedComponent) {
        this.appContainer.addComponent(applicationScopedComponent);
    }

    public void registerRequestScopedComponent(Class requestScopedComponent) {
        this.requestScopedComponents.add(requestScopedComponent);
        this.requestContainer.addComponent(requestScopedComponent);
    }

    private void addRequestScopedComponents() {
        requestScopedComponents.stream().forEach(requestContainer::addComponent);
    }

    public <T> T getComponent(Class<T> componentType) {
        return requestContainer.getComponent(componentType);
    }

    public void clearRequestScopedContainer() {
        this.appContainer.removeChildContainer(requestContainer);
        this.requestContainer = new DefaultPicoContainer(new Caching(), appContainer);
    }


    @Override
    public void register(Route route) {
        route.before();
    }

    @Override
    public void before(Request request, Response response) {
        clearRequestScopedContainer();
        requestContainer.addComponent(request);
        requestContainer.addComponent(response);
        addRequestScopedComponents();
    }
}
