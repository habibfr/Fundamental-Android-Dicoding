package com.habibfr.githubusersapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.DetailUser
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import com.habibfr.githubusersapp.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        private val TAB_TITLES = arrayOf(
            "Follower", "Following"
        )
        const val USERNAME = "username"
        const val TAG = "DetailUserActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val sectionsPagerAdapter = SectionsPagerAdapter(this)
//
//        val viewPager: ViewPager2 = binding.viewPager
//        val tabs: TabLayout = binding.tabs
//
//        viewPager.adapter = sectionsPagerAdapter
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = TAB_TITLES[position]
//        }.attach()
//
//        val username = intent.getStringExtra(USERNAME)
//
//        if (username != null) {
////            Log.d("username", username)
//
//            val bundle = Bundle()
//            bundle.putString("USERNAME", username)
//
//            val fragment = FollowerFragment()
//            fragment.arguments = bundle
//
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.detail_container, fragment)
//                .addToBackStack(null)
//                .commit()
//
//            detailUser(username)
//        }

//        supportActionBar?.elevation = 0f
    }

//    private fun detailUser(username: String) {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getDetailUser(
//            username,
//            "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"
//        )
//        client.enqueue(object : Callback<DetailUser> {
//            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUserData(responseBody)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure Response: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
//                Log.e(TAG, "onFailure: ${t.message}")
//
//            }
//
//        })
//    }
//
//    private fun setUserData(detailUser: DetailUser) {
//        binding.tvUsername.text = detailUser.login
//        binding.tvName.text = detailUser.name
//        Glide.with(this@DetailUserActivity)
//            .load(detailUser.avatarUrl)
//            .into(binding.imgAvatar)
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}