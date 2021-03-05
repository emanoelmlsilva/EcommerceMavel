package com.example.ecommercemarvel.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMarvel {

    private String API_KEY = "59f264a61d8968c09fc445e1f41052b7";
    private Retrofit retrofit;
    private MarvelService marvelService;

    public RetrofitMarvel(){
        Gson gson = new GsonBuilder().create();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {

            String timeStamp = "marvel";;
            String hash = "6c602ece1f6b8ffda02e36d7751c938b";

            Request request = chain.request();
            HttpUrl httpUrl = request.url().newBuilder()
                    .addQueryParameter("ts", timeStamp)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("hash", hash).build();
            return chain.proceed(request.newBuilder().url(httpUrl).build());

        }).build();

        retrofit = new Retrofit.Builder().baseUrl("https://gateway.marvel.com/v1/public/").addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();

        marvelService = retrofit.create(MarvelService.class);
    }

    public Retrofit getRetrofit(){
        return this.retrofit;
    }

    public MarvelService getMarvelService(){
        return marvelService;
    }
}
