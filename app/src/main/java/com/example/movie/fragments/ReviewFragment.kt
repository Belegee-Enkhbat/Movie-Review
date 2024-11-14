package com.example.movie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.model.MovieReviews
import com.example.movie.model.Reviews
import com.bumptech.glide.Glide // Add Glide for image loading (if you are using it)
import com.example.movie.service.MovieApiInterface
import com.example.movie.service.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : Fragment() {

    private lateinit var reviewTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var avatarImageView: ImageView

    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt("MOVIE_ID") // Get movieId from arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        reviewTextView = view.findViewById(R.id.reviewContentTextView)
        ratingTextView = view.findViewById(R.id.ratingTextView)
        usernameTextView = view.findViewById(R.id.usernameTextView)
        dateTextView = view.findViewById(R.id.dateTextView)
        avatarImageView = view.findViewById(R.id.avatarImageView)

        movieId?.let { fetchMovieReviews(it) } // Fetch reviews if movieId is available

        return view
    }

    private fun fetchMovieReviews(movieId: Int) {
        val movieApiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        val call: Call<MovieReviews> = movieApiService.getReviews(movieId)

        call.enqueue(object : Callback<MovieReviews> {
            override fun onResponse(call: Call<MovieReviews>, response: Response<MovieReviews>) {
                if (response.isSuccessful && response.body() != null) {
                    val reviews = response.body()!!
                    setReviews(reviews) // Call setReviews to display data
                } else {
                    Log.e("API Error", "Failed to fetch reviews. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieReviews>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }

    fun setReviews(movieReviews: MovieReviews) {
        Log.d("ReviewFragment", "Received reviews: ${movieReviews.results.size} reviews")

        if (movieReviews.results.isNotEmpty()) {
            for (review in movieReviews.results) {
                bindReview(review)
            }

        }
    }

    private fun bindReview(review: Reviews) {
        usernameTextView.text = review.author
        reviewTextView.text = review.content

        val ratingText = review.authorDetails.rating?.let {
            if (it > 0) {
                "Rating: ${review.authorDetails.rating}/10"
            } else {
                "Rating: Not rated"
            }
        }
        ratingTextView.text = ratingText

        dateTextView.text = review.createdAt.toString()

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${review.authorDetails.avatarPath}")
            .into(avatarImageView)

    }

    private fun formatDate(createdAt: String): String {
        val date = java.util.Date(createdAt.toLong())
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return formatter.format(date)
    }
}
