package com.example.movie.model

data class WatchListData(
    val media_type: String = "movie",
    val media_id: Int,
    val watchlist: Boolean = true
)
