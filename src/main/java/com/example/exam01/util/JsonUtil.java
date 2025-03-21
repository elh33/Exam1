// src/main/java/com/example/exam01/util/JsonUtil.java
package com.example.exam01.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<Date> dateSerializer = (date, type, context) ->
                date == null ? null : new JsonPrimitive(new SimpleDateFormat(DATE_FORMAT).format(date));

        JsonDeserializer<Date> dateDeserializer = (json, type, context) -> {
            try {
                return json == null ? null : new SimpleDateFormat(DATE_FORMAT).parse(json.getAsString());
            } catch (Exception e) {
                return null;
            }
        };

        gsonBuilder.registerTypeAdapter(Date.class, dateSerializer);
        gsonBuilder.registerTypeAdapter(Date.class, dateDeserializer);
        gsonBuilder.setPrettyPrinting();

        gson = gsonBuilder.create();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}