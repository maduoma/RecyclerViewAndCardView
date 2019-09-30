package com.dodemy.android.recyclerviewpluscardview;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.dodemy.android.recyclerviewpluscardview.ApiService.TMDbApiService;

import java.util.List;



//We create an instance of movieDataSource Class here
public class DataSourceFactory extends DataSource.Factory {

private Application application;
private MovieDataSource movieDataSource;
private TMDbApiService tmDbApiService;
private MutableLiveData<MovieDataSource> mutableLiveData;

    public DataSourceFactory(Application application, TMDbApiService tmDbApiService) {
        this.application = application;
        this.tmDbApiService = tmDbApiService;

        mutableLiveData = new MutableLiveData<>();
    }


    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(tmDbApiService, application);

        //we make the MovieDataSource a mutableLiveData so it can be observed by the model class
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }


    //generate a getter method for the mutableLiveData.
    // we will evoke this method from the model class
    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

    public LiveData<List<Genres>> getGenreIDs(){

        return movieDataSource.GetGenres();
    }


}
