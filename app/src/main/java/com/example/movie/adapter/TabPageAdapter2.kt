package com.example.movie.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movie.MovieDetailActivity
import com.example.movie.fragments.CastFragment
import com.example.movie.fragments.overview
import com.example.movie.fragments.ReviewFragment

class TabPagerAdapter2(activity: MovieDetailActivity,private val movieId: Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> overview()
            1 -> ReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("MOVIE_ID", movieId) // Pass movieId as an argument
                }
            }
            2 -> CastFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

}
