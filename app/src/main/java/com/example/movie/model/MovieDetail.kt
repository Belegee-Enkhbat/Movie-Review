package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("genres") val genres: List<Genre>
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
