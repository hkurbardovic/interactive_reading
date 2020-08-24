package com.hkurbardovic.interactivereading.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hkurbardovic.interactivereading.main.PageFragment

class ViewPagerAdapter(private val bookId: String, private val size: Int, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = PageFragment.newInstance(bookId, position)

    override fun getCount() = size
}