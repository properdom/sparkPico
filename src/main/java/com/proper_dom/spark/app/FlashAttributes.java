package com.proper_dom.spark.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FlashAttributes {

    static final String COOKIE_NAME = FlashAttributes.class.getName();
    private final ObjectMapper mapper;
    private final MapType mapType;
    private final CookieJar cookieJar;

    private Map<String, String> attributesIn;
    private Map<String, String> attributesOut;

    public FlashAttributes(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        this.attributesIn = new HashMap<>();
        this.attributesOut = new HashMap<>();
        this.mapper = new ObjectMapper();
        this.mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class);
    }

    public String get(String attribute) {
        return attributesIn.get(attribute);
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

    private String toJson(Map<String, String> map) {
        try {
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, map);
            return sw.toString();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> fromJson(String json) {
        if(json == null || json.length() == 0) {
            return new HashMap<>();
        }

        try {
            return mapper.readValue(json, mapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
