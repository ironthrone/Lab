package com.guo.lab;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ResponseDeserializer<T> implements JsonDeserializer<ResponseModel<T>> {

    private static final String DATA_KEY = "datas";
    private static final String STATE_KEY = "state";

    @Override
    public ResponseModel<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ResponseModel<T> responseModel = new ResponseModel<>();
        JsonObject response = (JsonObject) json;

        responseModel.state =  response.get(STATE_KEY).getAsInt();
        if (responseModel.state == 1) {
            responseModel.data = context.deserialize(response.get(DATA_KEY), ((ParameterizedType) typeOfT).getActualTypeArguments()[0]);
            responseModel.message = "";
        } else {
            responseModel.data = null;
            responseModel.message = response.get(DATA_KEY).getAsString();
        }
        return responseModel;
    }
}
