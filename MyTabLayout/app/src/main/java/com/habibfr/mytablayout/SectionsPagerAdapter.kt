package com.habibfr.mytablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var appName: String = ""

    //    override fun createFragment(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when(position){
//            0 -> fragment = HomeFragment()
//            1 -> fragment = ProfileFragment()
//        }
//
//        return fragment as Fragment
//    }
//
//    override fun getItemCount(): Int {
//        return 2
//    }


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment:Fragment = HomeFragment()

        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
            putString(HomeFragment.ARG_NAME, appName)
        }

        return fragment
    }
}