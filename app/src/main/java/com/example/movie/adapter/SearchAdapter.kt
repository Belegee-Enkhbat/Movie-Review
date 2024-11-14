package com.example.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.model.Movie

class SearchAdapter(
    private val context: Context,
    private val movieList: List<Movie>,
    private val itemLayout: Int,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<SearchAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie, context, onItemClick)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieImageView: ImageView = itemView.findViewById(R.id.movieImageSearchView)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieSearchTitle)
        private val movieRating: TextView = itemView.findViewById(R.id.movieSearchRating)
        private val movieGenre: TextView = itemView.findViewById(R.id.movieSearchGenre)
        private val movieDate: TextView = itemView.findViewById(R.id.movieSearchDate)
        private val movieDuration: TextView = itemView.findViewById(R.id.movieSearchDuration)

        fun bind(movie: Movie, context: Context, onItemClick: (Movie) -> Unit) {
            // Bind data to views
            movieTitle.text = movie.title
            movieRating.text = "${movie.voteAverage}/10"
            movieGenre.text = movie.genre // Assuming you have a method to format genres
            movieDate.text = "${movie.releaseDate}"
            movieDuration.text = "${movie.duration} vote counts" // Assuming runtime is in minutes

            // Load movie poster using Glide
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}") // Make sure this URL is valid
                .placeholder(R.drawable.img) // Placeholder image
                .into(movieImageView)

            // Set click listener for each movie item
            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}
