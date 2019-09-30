package com.dodemy.android.recyclerviewpluscardview.ApiService;

import com.dodemy.android.recyclerviewpluscardview.GenresResponse;
import com.dodemy.android.recyclerviewpluscardview.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TMDbApiService {

    @GET("movie/popular")
    Call<MovieResponse> getMoviesWithPage(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language

    );
    }




