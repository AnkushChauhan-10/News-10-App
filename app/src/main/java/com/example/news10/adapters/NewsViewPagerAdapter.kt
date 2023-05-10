package com.example.news10.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news10.fragments.*


class NewsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle, val list:Array<String>):
    FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = list[position].toLowerCase()
        return when(fragment){
            com.example.news10.utils.Constants.sportsFragment -> SportsFragment()
            com.example.news10.utils.Constants.technologyFragment -> TechnologyFragment()
            com.example.news10.utils.Constants.businessFragment -> BusinessFragment()
            com.example.news10.utils.Constants.entertainmentFragment -> EntertainmentFragment()
            com.example.news10.utils.Constants.healthFragment -> HealthFragment()
            com.example.news10.utils.Constants.scienceFragment -> ScienceFragment()
            com.example.news10.utils.Constants.generalFragment -> GeneralFragment()
            else -> GeneralFragment()
        }
    }
}