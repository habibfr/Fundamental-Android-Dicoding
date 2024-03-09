package com.habibfr.githubusersapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.GithubResponse
import com.habibfr.githubusersapp.data.response.Users
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import com.habibfr.githubusersapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    companion object {
//        private const val TAG = "MainActivity"
//        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
//        private val TAB_TITLES = arrayOf(
//            "follower", "following"
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

//        val layoutManager = LinearLayoutManager(this)
//        binding.rvUser.layoutManager = layoutManager
//
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvUser.addItemDecoration(itemDecoration)

//        with(binding) {
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { textView, actionId, event ->
//                    searchView.hide()
//                    rvUser.visibility = View.GONE
//
//                    val query = binding.searchView.text.toString().trim()
//                    findUsers(query)
//                    rvUser.visibility = View.VISIBLE
//
//                    false
//                }
//        }




//        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.search -> {
//                    binding.searchBar.visibility = View.VISIBLE
//                    binding.topAppBar.visibility = View.GONE
//                    true
//
//                }
//                else -> false
//            }
//        }

//        findUsers()
    }
//
//    private fun findUsers(username: String = "habibfr") {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUsers(
//            username,
//            "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"
//        )
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(
//                call: Call<GithubResponse>,
//                response: Response<GithubResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUser(responseBody.items)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }
//
//    private fun setUser(items: List<Users?>?) {
//        val adapter = UserAdapter()
//        adapter.submitList(items)
//        binding.rvUser.adapter = adapter
//
//
//        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
//            override fun onItemClicked(user: Users) {
//                showSelectedUser(user)
//            }
//
//        })
//    }
//
//    private fun showSelectedUser(user: Users) {
//        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
//        intent.putExtra(DetailUserActivity.USERNAME, user.login)
//        startActivity(intent)
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