package com.example.movie

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movie.adapter.TabPagerAdapter2
import com.example.movie.fragments.overview
import okhttp3.MediaType.Companion.toMediaType
import com.example.movie.fragments.ReviewFragment
import com.example.movie.model.MovieDetail
import com.example.movie.model.MovieResult
import com.example.movie.model.MovieReviews
import com.example.movie.model.WatchListData
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var back: ImageView
    private lateinit var movieObj: MovieDetail
    private lateinit var save: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)

        if (movieId != -1) {
            fetchMovieDetails(movieId)

            viewPager = findViewById(R.id.viewPager)
            tabLayout = findViewById(R.id.tab_layout)
            back = findViewById(R.id.back)
            back.setOnClickListener{
                finish()
            }

            save = findViewById(R.id.save)
            save.setOnClickListener{
                saveFunction(movieId)
            }

            val adapter = TabPagerAdapter2(this, movieId)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "About Movie"
                    1 -> "Reviews"
                    2 -> "Cast"
                    else -> ""
                }

                tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.green))
            }.attach()
        } else {
            Log.e("MovieDetailActivity", "Invalid movie ID")
        }
    }

    private fun fetchMovieDetails(id: Int) {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        val call: Call<MovieDetail> = movieApiService.getDetail(id)

        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful && response.body() != null) {
                    val movie = response.body()!!
                    movieObj = response.body()!!
                    findViewById<TextView>(R.id.title).text = movie.title
                    findViewById<TextView>(R.id.date).text = movie.releaseDate
                    findViewById<TextView>(R.id.time).text = "${movie.runtime} minutes"

                    val genreTextView = findViewById<TextView>(R.id.genre)
                    val firstGenreName = movie.genres.firstOrNull()?.name ?: "No Genre"
                    genreTextView.text = firstGenreName

                    val overviewFragment = supportFragmentManager.findFragmentByTag("f0") as? overview
                    overviewFragment?.setMovieOverView(movie.overview)

                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .into(findViewById<ImageView>(R.id.movieImageView))
                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                        .into(findViewById<ImageView>(R.id.moviePosterImageView))
                } else {
                    Log.e("API Error", "Failed to fetch movie details. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    };

    fun saveFunction(movieId: Int) {
        val sharedPreferences = getSharedPreferences("WatchlistPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("MOVIE_DETAIL", Gson().toJson(movieObj))
        editor.apply()
    }



}
