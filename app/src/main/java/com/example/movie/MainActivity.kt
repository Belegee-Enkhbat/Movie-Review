package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movie.adapter.MovieAdapter
import com.example.movie.adapter.SearchAdapter
import com.example.movie.adapter.TabPagerAdapter
import com.example.movie.model.MovieResult
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : FragmentActivity() {
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var topTrendingRecyclerView: RecyclerView
    private lateinit var search: EditText
    private lateinit var search_btn: Button
    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        topTrendingRecyclerView = findViewById(R.id.recyclerView)
        topTrendingRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        fetchTopTrendingMovies() // Fetch the top trending movies

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tab_layout)
        search = findViewById(R.id.search_bar)
        search_btn = findViewById(R.id.search_button)

        search_btn.setOnClickListener {
            val query = search.text.toString()
            if (query.isNotEmpty()) { // Check if the query is not empty
                Log.d("Search", "Search query: $query") // Log the query
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("SEARCH_QUERY", query) // Pass the query to the SearchActivity
                startActivity(intent)
            } else {
                Log.d("Search", "Search query is empty")
            }
        }

        val adapter = TabPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Upcoming"
                1 -> "Top Rated"
                2 -> "Now Playing"
                3 -> "Popular"
                else -> ""
            }

            tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.green))
        }.attach()

        nav = findViewById(R.id.bottom_navigation)

        // Set an item selected listener to start activities based on the item clicked
        nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_movies -> {
                    // Start HomeActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_watchlist -> {
                    // Start SearchActivity
                    val intent = Intent(this, WatchList::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }

    }}

    private fun fetchTopTrendingMovies() {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        val call: Call<MovieResult> = movieApiService.getMovieList() // Update this to your API endpoint

        call.enqueue(object : Callback<MovieResult> {
            override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                if (response.isSuccessful) {
                    val movieResult: MovieResult? = response.body()
                    movieResult?.results?.let { movies ->
                        movieAdapter = MovieAdapter(movies,R.layout.movie_item,R.id.imageView){
                            movies ->
                            val intent = Intent(this@MainActivity, MovieDetailActivity::class.java).apply {
                                putExtra("MOVIE_ID", movies.id)
                            }
                            startActivity(intent)
                        }
                        topTrendingRecyclerView.adapter = movieAdapter // Set the adapter to the RecyclerView
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
