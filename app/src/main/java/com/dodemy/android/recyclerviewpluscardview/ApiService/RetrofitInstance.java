package com.dodemy.android.recyclerviewpluscardview.ApiService;

import com.dodemy.android.recyclerviewpluscardview.moviesAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitInstance {

    private moviesAdapter Adapter;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String Language = "en-us";
    private static Retrofit retrofit= null;

    public static TMDbApiService getRetrofitInstance() {

        if(retrofit == null) {
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    return retrofit.create(TMDbApiService.class);
    }
}
