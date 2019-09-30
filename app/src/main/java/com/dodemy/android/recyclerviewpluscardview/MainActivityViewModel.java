package com.dodemy.android.recyclerviewpluscardview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.dodemy.android.recyclerviewpluscardview.ApiService.RetrofitInstance;
import com.dodemy.android.recyclerviewpluscardview.ApiService.TMDbApiService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



//The viewModel class will help to bind the livedata to the view(activity)
public class  MainActivityViewModel extends AndroidViewModel {

    private LiveData<MovieDataSource> movieDataSourceLiveData;


    //create a live data object for the pagedList of movies to be gotten from the dataSource
    private LiveData<PagedList<Movies>> moviesPagedList;

    //create an  executor to fetch the data into the pagedList asynchronously
    private Executor executor;
    private final DataSourceFactory factory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        //use the viewModel to get a movieDataSource factory to use on the MainActivity
        TMDbApiService tmDbApiService = RetrofitInstance.getRetrofitInstance();
        factory = new DataSourceFactory(application,tmDbApiService);

        // we make the Datasource instance gotten with factory a liveData source
        movieDataSourceLiveData = factory.getMutableLiveData();

        //create a pagedList to hold the data set to be loaded into a view with a pagedListAdapter
        PagedList.Config config = (new PagedList.Config.Builder())
                                .setEnablePlaceholders(true)
                                .setInitialLoadSizeHint(20)
                                .setPageSize(20)
                                .setPrefetchDistance(4)
                                .build();
        executor = Executors.newFixedThreadPool(3);

        //the moviesPagedList to be loaded with movie data with the
        // dataSourceFactory from repository
        moviesPagedList = (new LivePagedListBuilder<Integer, Movies>(factory,config))
                .setFetchExecutor(executor)
                .build();

    }

    //create a getter method for the moviesPagedList
    public LiveData<PagedList<Movies>> getMoviesPagedList() {
        return moviesPagedList;


    }

    public LiveData<List<Genres>> getGenreIDsFromFactory(){
        return factory.getGenreIDs();
    }



}
