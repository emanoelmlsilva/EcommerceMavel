package com.example.ecommercemarvel.service;

import com.example.ecommercemarvel.model.ResponseComic;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("comics")
    Observable<ResponseComic> getComics(@Query("limit") int limit);
}