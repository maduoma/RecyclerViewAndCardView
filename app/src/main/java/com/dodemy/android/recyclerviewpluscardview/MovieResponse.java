package com.dodemy.android.recyclerviewpluscardview;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class MovieResponse {

    @SerializedName("results")
    private List<Movies> results;


    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("page")
    private Integer pageNumber;


    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
       this.results = results;
    }


}
