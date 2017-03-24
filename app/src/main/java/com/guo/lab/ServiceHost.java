package com.guo.lab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/3/24.
 */

public class ServiceHost {
    private static final String BASE_URL = "http://60.205.208.90/mobile/";

    private static ServiceHost INSTANCE;
    private final Retrofit retrofit;
    private Service service;

    public ServiceHost getInsatnce() {
        if (INSTANCE == null) {
            synchronized (ServiceHost.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceHost();
                }
            }
        }
        return INSTANCE;
    }

    private ServiceHost() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ResponseModel.class,
                new ResponseDeserializer());

        Gson customGson = gsonBuilder.create();

        //配置OkHttp日志
        HttpLoggingInterceptor.Level logLevel = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(logLevel);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(customGson))
                .client(okHttpClient)
                .build();
    }

    public Service getService() {
        if (service == null) {
            service = retrofit.create(Service.class);
        }
        return service;
    }

    public <S> S getService(Class<S> clazz) {
        return retrofit.create(clazz);
    }
}
