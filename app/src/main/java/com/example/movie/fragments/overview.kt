package com.example.movie.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.movie.R

class overview : Fragment() {
   private lateinit var overView: TextView
   private var movieOverView: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        overView = view.findViewById(R.id.overviewTextView)
        overView.text = movieOverView // Set initial text
        return view
    }

    fun setMovieOverView(overview: String){
        overView.text = overview
        Log.d("AboutMovieFragment", "Movie Overview: $overview")
    }

}