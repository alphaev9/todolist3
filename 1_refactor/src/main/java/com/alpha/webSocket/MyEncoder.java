package com.alpha.webSocket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

public class MyEncoder implements javax.websocket.Encoder.Text<Object> {
    public String encode(Object o) throws EncodeException {
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        String messageType;
        switch (o.getClass().getSimpleName()) {
            case "String":
                messageType = "kickOff";
                break;
            case "ArrayList":
                messageType = "advent";
                break;
            default:
                messageType = "empty";
        }
        jsonObject.addProperty("messageType", messageType);
        JsonElement messageBody = gson.toJsonTree(o);
        jsonObject.add("messageBody", messageBody);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonObject.toString());
        return gson.toJson(jsonElement);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
