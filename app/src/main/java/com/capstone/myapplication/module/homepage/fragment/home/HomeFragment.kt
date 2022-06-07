package com.capstone.myapplication.module.homepage.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.myapplication.R
import com.capstone.myapplication.base.BaseFragment
import com.capstone.myapplication.databinding.FragmentHomeBinding
import com.capstone.myapplication.module.homepage.fragment.home.trash.TrashFragment
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGES = 2

class HomeFragment
    : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ScreenSlidePagerAdapter(this)
        TabLayoutMediator(binding.organicTabL, binding.viewPager){ tab, position ->
            if(position == 0)
                tab.text = getString(R.string.organic)
            else
                tab.text = getString(R.string.inorganic)
        }.attach()
    }
    /**
     * A simple pager adapter that represents 2 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int) = TrashFragment()
    }
}