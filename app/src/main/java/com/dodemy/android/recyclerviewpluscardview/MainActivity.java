package com.dodemy.android.recyclerviewpluscardview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dodemy.android.recyclerviewpluscardview.ApiService.TMDbApiService;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    private moviesAdapter mMoviesAdapter;
    private RecyclerView movieRecyclerView;
    private TMDbApiService tmDbApi;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "be44f9cb6ab3f64b9c91466c0ce1160c";
    private static final String Language = "en-us";
    private PagedList<Movies> movieList;
    private List<Genres> GenresList;

    private ViewDataBinding viewDataBinding;
    private MainActivityViewModel mainActivityViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //initializing the recyclerView member variable
        movieRecyclerView = findViewById(R.id.movie_list);

        getPopularMovies();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







//helper method to get the popular movies by page using the mainActivityModel class
public void getPopularMovies(){
        mainActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movies>>() {
            @Override
            public void onChanged(PagedList<Movies> moviesFromLiveData) {
            movieList =moviesFromLiveData;
            showInRecyclerView();
            }
        });
}

// helper method to attach the pagedList of data to the recycler view
public void showInRecyclerView(){
        //get the GenresList using the viewModel
    mainActivityViewModel.getGenreIDsFromFactory().observe(this, new Observer<List<Genres>>() {
        @Override
        public void onChanged(List<Genres> genresList) {
            if(genresList !=null)
            GenresList = genresList;

            mMoviesAdapter = new moviesAdapter(GenresList,MainActivity.this);
            mMoviesAdapter.submitList(movieList);
            movieRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            movieRecyclerView.setItemAnimator(new DefaultItemAnimator());
            movieRecyclerView.setAdapter(mMoviesAdapter);
            mMoviesAdapter.notifyDataSetChanged();

        }
    });


}







}
