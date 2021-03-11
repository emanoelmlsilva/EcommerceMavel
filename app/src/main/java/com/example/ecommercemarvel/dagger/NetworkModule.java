package com.example.ecommercemarvel.dagger;

import com.example.ecommercemarvel.service.MarvelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private String API_KEY = "59f264a61d8968c09fc445e1f41052b7";

    @Provides
    public Gson provideGson(){
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    public OkHttpClient provideOkHttpClient(){

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
        return client;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gateway.marvel.com/v1/public/").addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
        return retrofit;
    }

    @Singleton
    @Provides
    public MarvelService provideMarvelService(Retrofit retrofit){
        return retrofit.create(MarvelService.class);
    }
}
