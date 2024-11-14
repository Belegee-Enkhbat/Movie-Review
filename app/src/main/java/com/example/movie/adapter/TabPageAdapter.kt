package com.example.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movie.MainActivity
import com.example.movie.fragments.NowPlayingFragment
import com.example.movie.fragments.PopularFragment
import com.example.movie.fragments.TopRatedFragment
import com.example.movie.fragments.UpComingFragment

class TabPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingFragment()
            1 -> PopularFragment()
            2 -> TopRatedFragment()
            3 -> UpComingFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
