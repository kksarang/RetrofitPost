package com.example.mycamera;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String BASE_URL ="http://192.168.137.1/Android%20Tutorials/";

    private static RetroClient myclient;
    private Retrofit retrofit;

    private RetroClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetroClient getInstance(){

        if (myclient ==null )
        {
            myclient =new  RetroClient();
        }

        return myclient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }


}
