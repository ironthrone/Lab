package com.guo.lab;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ResponseDeserializer<T> implements JsonDeserializer<ResponseModel<T>> {
    private Gson helperGson;

    public ResponseDeserializer(Gson helperGson) {
        this.helperGson = helperGson;
    }

    @Override
    public ResponseModel<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResponseModel<T> responseModel = new ResponseModel<>();
        JsonObject response = (JsonObject) json;

        responseModel.state =  response.get("state").getAsInt();
        if (responseModel.state == 1) {
            response.get("datas").toString();
            responseModel.datas = helperGson.fromJson(response, new TypeToken<T>(){}.getType());
            responseModel.err = null;
        } else {
            responseModel.datas = null;
            responseModel.err = response.get("datas").getAsString();

        }
        return responseModel;
    }
}
