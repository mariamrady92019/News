package com.route.apis;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Nabil Mohamed on 10/18/2019.
 * m.nabil.fci2015@gmail.com
 */
public class ApiManager {


    private static Retrofit retrofit;

    private static Retrofit getInstance(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WebServices getApis(){
        return getInstance().create(WebServices.class);
    }
}
