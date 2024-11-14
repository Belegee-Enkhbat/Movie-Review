package com.example.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.adapter.CastAdapter
import com.example.movie.model.MovieCast
import com.example.movie.model.Cast
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastFragment : Fragment() {

    private lateinit var castRecyclerView: RecyclerView
    private lateinit var castAdapter: CastAdapter
    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt("MOVIE_ID") // Get movieId from arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cast, container, false)

        castRecyclerView = view.findViewById(R.id.castRecyclerView)
        castRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        castAdapter = CastAdapter(emptyList())
        castRecyclerView.adapter = castAdapter

        movieId?.let { fetchMovieCast(it) }

        return view
    }

    private fun fetchMovieCast(movieId: Int) {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        val call: Call<MovieCast> = movieApiService.getCast(movieId)

        call.enqueue(object : Callback<MovieCast> {
            override fun onResponse(call: Call<MovieCast>, response: Response<MovieCast>) {
                if (response.isSuccessful && response.body() != null) {
                    val cast = response.body()!!
                    Log.d("D","SDA")
                    castAdapter.updateCastList(cast.cast)
                } else {
                    Log.e("API Error", "Failed to fetch cast. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieCast>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }
}
