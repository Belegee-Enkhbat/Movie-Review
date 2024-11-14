package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("vote_count") val duration: String,


    // Add other fields as needed
)

data class MovieResult(
    @SerializedName("results") val results: List<Movie>
)
