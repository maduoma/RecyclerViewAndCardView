package com.dodemy.android.recyclerviewpluscardview;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.dodemy.android.recyclerviewpluscardview.ApiService.TMDbApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



//Create a DataSource class to take advantaee of Paging library for loading movie pages
public class MovieDataSource extends PageKeyedDataSource<Integer, Movies> {
    private static final String Language = "en-us";
    private List<Movies> movieList;
    private MutableLiveData<List<Genres>> GenresList;

    private  Integer FIRST_PAGE = 1;
    private TMDbApiService tmDbApiService;
    private Application application;

    public MovieDataSource(TMDbApiService tmDbApiService, Application application ) {
        this.tmDbApiService = tmDbApiService;
        this.application = application;
        this.GenresList = new MutableLiveData<>();
    }


    //method for loading the first page of movies from the API
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Movies> callback) {


                    //get movies from first page
                    Call<MovieResponse> callMovies = tmDbApiService.getMoviesWithPage(FIRST_PAGE ,
                            application.getApplicationContext().getString(R.string.api_key));
                    callMovies.enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            assert response.body()!=null;
                            if(response.body()!=null ){
                                movieList = response.body().getResults();
                                callback.onResult(movieList,null,2);
                                Log.d("Number of movies:", String.valueOf(movieList.size()));
                                //mMoviesAdapter =new moviesAdapter( Genres, movieList, MainActivity.this);
                                // movieRecyclerView.setAdapter(mMoviesAdapter);


                            }

                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {
                            //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });






    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Movies> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Movies> callback) {


        Call<MovieResponse> call = tmDbApiService.getMoviesWithPage(params.key,
                application.getApplicationContext().getString(R.string.api_key));
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                assert response.body()!=null;
                if(response.body()!=null ){
                    movieList = response.body().getResults();
                    Log.d("Number of movies:", String.valueOf(movieList.size()));
                    //mMoviesAdapter =new moviesAdapter( Genres, movieList, MainActivity.this);
                    // movieRecyclerView.setAdapter(mMoviesAdapter);

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        callback.onResult(movieList,params.key+1);


    }


//helper method for loading the movies and their genres
    public LiveData<List<Genres>> GetGenres(){
        Call<GenresResponse> genresResponseCall = tmDbApiService.getGenres(
                application.getApplicationContext().getString(R.string.api_key),
                Language);
        genresResponseCall.enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if(response.body()!=null && response.body().getGenreIds()!=null){
                    GenresList.postValue(response.body().getGenreIds());
                }

            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {

            }
        });

        return GenresList;
    }









}
