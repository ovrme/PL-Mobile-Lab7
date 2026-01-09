package com.example.expensetracker.data.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private SimpleDateFormat formatter() {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf;
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) return null;
        return new JsonPrimitive(formatter().format(src));
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String s = json.getAsString();
            try { return formatter().parse(s); }
            catch (ParseException ex) {
                SimpleDateFormat alt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                alt.setTimeZone(TimeZone.getTimeZone("UTC"));
                return alt.parse(s);
            }
        } catch (Exception e) {
            throw new JsonParseException("Invalid ISO8601 date: " + json, e);
        }
    }
}