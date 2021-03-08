package com.example.ecommercemarvel.service;
import com.example.ecommercemarvel.model.ResponseComic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("comics")
    Call<ResponseComic> getComics(@Query("limit") int limit);
}
