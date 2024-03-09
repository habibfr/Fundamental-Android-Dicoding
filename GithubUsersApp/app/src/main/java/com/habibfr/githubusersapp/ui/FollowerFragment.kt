package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.UserFollower
import com.habibfr.githubusersapp.data.response.UserFollowerItem
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    private lateinit var rvFollower: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var tv_follower_kosong: TextView


    companion object {
        const val USERNAME = "username"
        const val TAG = "FollowerFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower = view.findViewById(R.id.rvFollower)
        progressBar = view.findViewById(R.id.progressBar)
        tv_follower_kosong = view.findViewById(R.id.tv_follower_kosong)

        val layoutManager = LinearLayoutManager(activity)
        rvFollower.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvFollower.addItemDecoration(itemDecoration)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val followerViewModel = ViewModelProvider(requireActivity())[FollowerViewModel::class.java]

        followerViewModel.follower.observe(viewLifecycleOwner){follower ->
            setFollower(follower)
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


//        if (arguments != null) {
//            val username = arguments?.getString(USERNAME)
//            if (username != null) {
//                findFollower(username)
//                showLoading(false)
//            }
//        }

//        findFollower()

        sharedViewModel.selectedItem.observe(viewLifecycleOwner) { username ->
            followerViewModel.findFollower(username)
        }
    }


//    private fun findFollower(username: String = "habibfr") {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUserFollower(
//            username,
//            "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"
//        )
//        client.enqueue(object : Callback<List<UserFollowerItem>> {
//            override fun onResponse(
//                call: Call<List<UserFollowerItem>>, response: Response<List<UserFollowerItem>>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setFollower(responseBody)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure follower response : ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserFollowerItem>>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure get follower: ${t.message}")
//            }
//        })
//    }

    private fun setFollower(userFollower: List<UserFollowerItem?>?) {

        if (userFollower?.isEmpty() == true){
            tv_follower_kosong.visibility = View.VISIBLE
        }else{
            tv_follower_kosong.visibility = View.GONE
        }

        val adapter = FollowerAdapter()
        adapter.submitList(userFollower)
        rvFollower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}