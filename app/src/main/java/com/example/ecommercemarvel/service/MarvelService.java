package com.example.ecommercemarvel.service;
import com.example.ecommercemarvel.model.ResponseComic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MarvelService {
    @GET("comics?limit={limit}")
    Call<ResponseComic> getComics(@Path("limit") int limit);
}
