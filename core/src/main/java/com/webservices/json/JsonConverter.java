package com.webservices.json;

import com.google.gson.Gson;

import java.util.ArrayList;

public class JsonConverter {
    private Gson gson;

    public JsonConverter() {
        gson = new Gson();
    }

    public String convertToJson(Object object){
        return gson.toJson(object);
    }

    private static void createJsonResponse() {
        var names = new Names();
        names.names = new ArrayList<>();
        names.names.add(new Name("Kajsa", "Anka"));

        JsonConverter converter = new JsonConverter();

        var json = converter.convertToJson(names);
        System.out.println(json);
    }
}
