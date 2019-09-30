package com.dodemy.android.recyclerviewpluscardview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {



    @SerializedName("vote_average")
    @Expose
    private float rating;


    @SerializedName("id")
        private int id;


    @SerializedName("genre_ids")
        private List<Integer> GenreIDs;

        @SerializedName("release_date")
        private String ReleaseDate;

        @SerializedName("title")
        private String movieTitle;

    @SerializedName("poster_path")
        private String posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public String getPosterPath() {
        return posterPath;
    }
    public List<Integer> getGenreIDs() {
        return GenreIDs;
    }


    public void setGenreIDs(List<Integer> genreIDs) {
        this.GenreIDs = genreIDs;
    }

    public void setReleaseDate(String releaseDate) {
        this.ReleaseDate = releaseDate;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }


    //create a DiffUtil abstract class to be used to efficiently update the recyclerView

    public static final DiffUtil.ItemCallback<Movies> CALLBACK =
            new DiffUtil.ItemCallback<Movies>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movies oldItem, @NonNull Movies newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Movies oldItem, @NonNull Movies newItem) {
                    return true;
                }
            };

}
