package com.habibfr.myflexiblefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()

        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if(fragment !is HomeFragment){
            Log.d("My FlexibleFragment", "Fragment name : " + HomeFragment::class.java.simpleName)

            fragmentManager
                .beginTransaction()
                .add(R.id.fLContainer, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}