package com.dodemy.android.recyclerviewpluscardview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class moviesAdapter extends PagedListAdapter<Movies,moviesAdapter.movieViewHolder> {
    private ImageView movieImage;
    private TextView movieTitle, movieGenre, movieYear, movieRating;
    //comment out the List array of movies object since the pagedListAdapter takes care of
    //fetching the movie from the Data source
    //private List<Movies> movies;
    private Context context;
    private List<Genres> genresList;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public moviesAdapter( List<Genres> genresList, Context context){
        super(Movies.CALLBACK);
        //this.movies = moviesList;
        this.context = context;
        this.genresList=genresList;
    }




    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item,parent,false);


        return new movieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        //use the pagedList getItem() method to get the movie from the PagedList
        holder.bind(getItem(position));
    }

  //getItemCount() method no longer needed


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    //create inner viewHolder class
    public class movieViewHolder extends RecyclerView.ViewHolder{



        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            //get the itemView ids here
            movieImage = itemView.findViewById(R.id.movie_poster);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieGenre = itemView.findViewById(R.id.movie_genre);
            movieYear = itemView.findViewById(R.id.movie_year);
            movieRating = itemView.findViewById(R.id.movie_rating);

        }

        public void bind(Movies movie){
            movieTitle.setText(movie.getMovieTitle());
            movieGenre.setText(getGenres(movie.getGenreIDs()));
            movieYear.setText(movie.getReleaseDate().split("-")[0]);
            movieRating.setText(String.valueOf(movie.getRating()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(movieImage);
        }

        public String getGenres(List<Integer> movieGenreIds){
            List<String> movieGenres = new ArrayList<>();
            for (Integer movieGenreId : movieGenreIds) {
                for (Genres genre : genresList) {
                    if (genre.getId() == movieGenreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }


    }
}