package com.ahanuliak.iwatch.data.response


import com.google.gson.annotations.SerializedName

data class MoviesResponce(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)