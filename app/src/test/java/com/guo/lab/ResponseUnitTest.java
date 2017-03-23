package com.guo.lab;

import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ResponseUnitTest {

    private static final String TAG = ResponseUnitTest.class.getSimpleName();
    private static Gson customGson;
    @Before
    public  void setup() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ResponseModel.class, new ResponseDeserializer<>(new Gson()));
        customGson = gsonBuilder.create();
    }

    @Test
    public void parseDataIsObject() throws Exception {
        String json = "{\"state\":1,\"datas\":{\"member_name\":\"xiaofeng\",\"member_id\":11}}";
        ResponseModel<MemberModel> responseModel = customGson.fromJson(json, new TypeToken<ResponseModel<MemberModel>>() {
        }.getType());
        Assert.assertTrue(responseModel.datas != null );
        Assert.assertTrue(responseModel.datas instanceof MemberModel);
    }

    @Test
    public void parseDataIsString() throws Exception {
        String json = "{\"state\":0,\"datas\":\"member_name\"}";
        ResponseModel<String> responseModel = customGson.fromJson(json, new TypeToken<ResponseModel<String>>() {
        }.getType());
        System.out.println(responseModel.toString());
        Assert.assertTrue(responseModel.err != null);
        Assert.assertTrue(responseModel.err instanceof String );
    }
    @Test
    public void parseDataIsList() throws Exception {
        String json = "{\"state\":1,\"datas\":[{\"member_name\":\"xiaofeng\",\"member_id\":11},{\"member_name\":\"xiaofeng\",\"member_id\":11}]}";
        ResponseModel<ArrayList<MemberModel>> responseModel = customGson.fromJson(json, new TypeToken<ResponseModel<ArrayList<MemberModel>>>() {
        }.getType());
        System.out.println(responseModel.toString());
        Assert.assertTrue(responseModel.datas instanceof ArrayList );
        Assert.assertTrue(responseModel.datas != null );
    }
}