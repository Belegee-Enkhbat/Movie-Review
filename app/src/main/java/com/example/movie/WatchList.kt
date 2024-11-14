package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.adapter.SearchAdapter
import com.example.movie.model.Movie
import com.example.movie.model.MovieDetail
import com.google.gson.Gson

class WatchList : AppCompatActivity() {

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.watchlist)

        searchRecyclerView = findViewById(R.id.recyclerViewWatchListMovies)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        back = findViewById(R.id.backWatchlist)
        back.setOnClickListener{
            finish()
        }

        fetchWatchListMovies()
    }

    private fun fetchWatchListMovies() {
        val sharedPreferences = getSharedPreferences("WatchlistPrefs", MODE_PRIVATE)
        val movieJson = sharedPreferences.getString("MOVIE_DETAIL", null)

        if (movieJson != null) {
            val movieDetail = Gson().fromJson(movieJson, MovieDetail::class.java)
            val genreNames = movieDetail.genres.joinToString(", ") { it.name }
            val movies = listOf(
                Movie(
                    id = movieDetail.id,
                    title = movieDetail.title,
                    overview = movieDetail.overview,
                    posterPath = movieDetail.posterPath,
                    duration = movieDetail.runtime.toString(),
                    genre = genreNames,
                    releaseDate = movieDetail.releaseDate,
                    voteAverage = movieDetail.voteAverage
                )
            )

            searchAdapter = SearchAdapter(this, movies, R.layout.movie_item_search) { movie ->
                saveMovieIdToLocalMemory(movie.id)
                openMovieDetail(movie)
            }
            searchRecyclerView.adapter = searchAdapter
        } else {
            Log.e("WatchList", "No movies found in watchlist")
        }
    }

    private fun saveMovieIdToLocalMemory(movieId: Int) {
        val sharedPreferences = getSharedPreferences("WatchListPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("LAST_MOVIE_ID", movieId)
        editor.apply()
    }

    private fun openMovieDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }
}