package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class MovieCast(
    @SerializedName("cast")
    val cast: List<Cast>
)

data class Cast(
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?
)
