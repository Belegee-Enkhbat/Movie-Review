package com.example.movie.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.MovieDetailActivity
import com.example.movie.R
import com.example.movie.adapter.MovieAdapter
import com.example.movie.model.Movie
import com.example.movie.model.MovieResult
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_toprated, container, false)

        // Initialize RecyclerView and set layout manager
        recyclerView = view.findViewById(R.id.recycler_view_toprated)
        recyclerView.layoutManager = GridLayoutManager(context, 3)  // 2 columns

        // Fetch Now Playing movies from API
        fetchNowPlayingMovies()

        return view
    }

    private fun fetchNowPlayingMovies() {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        val call: Call<MovieResult> = movieApiService.getMovieList() // Update this to your API endpoint

        call.enqueue(object : Callback<MovieResult> {
            override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                if (response.isSuccessful) {
                    val movieResult: MovieResult? = response.body()
                    movieResult?.results?.let { movies ->
                        movieAdapter = MovieAdapter(movies,R.layout.item_movie,R.id.movie_image) {movie ->
                            val intent = Intent(activity, MovieDetailActivity::class.java).apply {
                                putExtra("MOVIE_ID", movie.id)
                                putExtra("MOVIE_TITLE", movie.title)
                                putExtra("MOVIE_POSTER", movie.posterPath)
                            }
                            startActivity(intent)
                        }
                        recyclerView.adapter = movieAdapter // Set the adapter to the RecyclerView
                    }
                } else {
                    Log.e("API Error", "Failed to fetch top trending movies. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }
}
