package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.UserFollowingItem
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.*


class FollowingFragment : Fragment() {
    private lateinit var rvFollowing: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var tv_following_kosong: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = view.findViewById(R.id.rvFollowing)
        progressBar = view.findViewById(R.id.progressBar)
        tv_following_kosong = view.findViewById(R.id.tv_following_kosong)

        val layoutManager = LinearLayoutManager(activity)
        rvFollowing.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvFollowing.addItemDecoration(itemDecoration)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val followingViewModel =
            ViewModelProvider(requireActivity())[FollowingViewModel::class.java]

        followingViewModel.follower.observe(viewLifecycleOwner) { following ->
            setFollowing(following)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


//        findFollowing()

        sharedViewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            followingViewModel.findFollowing(item)
        }
    }

    //    private fun findFollowing(username: String = "habibfr") {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUserFollowing(
//            username,
//            "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"
//        )
//
//        client.enqueue(object : Callback<List<UserFollowingItem>> {
//            override fun onResponse(call: Call<List<UserFollowingItem>>, response: Response<List<UserFollowingItem>>) {
//                if (response.isSuccessful) {
//                    showLoading(false)
//                    val userFollowingItems = response.body()
//                    setFollowing(userFollowingItems)
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserFollowingItem>>, t: Throwable) {
//                showLoading(false)
//                Log.e("ERROR GET FOLLOWING", "onFailure: ${t.message}")
//            }
//        })
//    }
    private fun setFollowing(userFollowing: List<UserFollowingItem?>?) {

        if (userFollowing?.isEmpty() == true) {
            tv_following_kosong.visibility = View.VISIBLE
        } else {
            tv_following_kosong.visibility = View.GONE
        }

        val adapter = FollowingAdapter()
        adapter.submitList(userFollowing)
        rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}