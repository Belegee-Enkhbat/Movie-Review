package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.model.Movie

class MovieAdapter(
    private val movieList: List<Movie>,
    private val layoutResId: Int,
    private val imageViewResId: Int,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(imageViewResId)

        fun bind(movie: Movie){
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${movie.posterPath}").into(
                imageView
            )
            imageView.setOnClickListener{
                clickListener(movie)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movieList.size
}
