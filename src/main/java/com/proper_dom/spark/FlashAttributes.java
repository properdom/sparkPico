package com.proper_dom.spark;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FlashAttributes {

    static final String COOKIE_NAME = FlashAttributes.class.getName();
    private Map attributesIn;
    private Map attributesOut;
    private CookieJar cookieJar;

    public FlashAttributes(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        this.attributesIn = new HashMap<>();
        this.attributesOut = new HashMap<>();
    }

    public String get(String attribute) {
        return (String) attributesIn.get(attribute);
    }

    public boolean isEmpty() {
        return attributesIn.isEmpty();
    }

    public void put(String attribute, String value) {
        attributesOut.put(attribute, value);
    }

    public void load() {
        attributesIn = fromJson(cookieJar.get(COOKIE_NAME));
        cookieJar.remove(COOKIE_NAME);
    }

    public void store() {
        if(!attributesOut.isEmpty()) {
            String json = toJson(attributesOut);
            cookieJar.add(COOKIE_NAME, json);
            attributesOut.clear();
        }
    }

    private String toJson(Object out) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, out);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Map fromJson(String json) {
        if(json == null || json.length() == 0) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
