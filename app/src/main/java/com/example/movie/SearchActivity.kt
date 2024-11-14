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
import com.example.movie.model.MovieResult
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var query: String
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchmovies)

        searchRecyclerView = findViewById(R.id.recyclerViewSearchedMovies)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        back = findViewById(R.id.backSearch)
        back.setOnClickListener{
            finish()
        }
        query = intent.getStringExtra("SEARCH_QUERY").toString()

        fetchSearchMovies(query)
    }

    private fun fetchSearchMovies(query: String) {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val call: Call<MovieResult> = movieApiService.getSearch(query)

        call.enqueue(object : Callback<MovieResult> {
            override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                Log.d("API Response", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    val movieResult: MovieResult? = response.body()
                    Log.d("SearchActivity", "Movies fetched: ${movieResult?.results?.size}")
                    movieResult?.results?.let { movies ->
                        searchAdapter = SearchAdapter(this@SearchActivity, movies, R.layout.movie_item_search) { movie ->
                            openMovieDetail(movie)
                        }
                        searchRecyclerView.adapter = searchAdapter
                    }
                } else {
                    Log.e("API Error", "Failed to fetch search results. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }

    private fun openMovieDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        startActivity(intent)
    }
}
