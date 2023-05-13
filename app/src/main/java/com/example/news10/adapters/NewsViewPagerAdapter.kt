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
        return AllNewsFragment(fragment)
    }
}