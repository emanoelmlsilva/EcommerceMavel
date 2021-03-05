package com.example.ecommercemarvel.service;
import com.example.ecommercemarvel.model.ResponseComic;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MarvelService {
    @GET("comics")
    Call<ResponseComic> getComics();
}
