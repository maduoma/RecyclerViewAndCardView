package com.dodemy.android.recyclerviewpluscardview;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse {



    @SerializedName("genres")
    private List<Genres> genreIds;

    public List<Genres> getGenreIds() {
        return genreIds;
    }

}
