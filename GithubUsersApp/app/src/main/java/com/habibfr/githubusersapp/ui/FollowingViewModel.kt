package com.habibfr.githubusersapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibfr.githubusersapp.data.response.UserFollowingItem
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _following = MutableLiveData<List<UserFollowingItem?>?>()
    val following: LiveData<List<UserFollowingItem?>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var isFollowingLoaded: Boolean = false
        private set
    var currentUsername: String? = null

    companion object {
        private const val TAG = "FollowingFragment"
    }

    fun findFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(
            username
        )

        client.enqueue(object : Callback<List<UserFollowingItem>> {
            override fun onResponse(
                call: Call<List<UserFollowingItem>>,
                response: Response<List<UserFollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userFollowingItems = response.body()
                    _following.value = userFollowingItems
                    isFollowingLoaded = true
                } else {
                    Log.e(TAG, "onFailure Res: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowingItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
        currentUsername = username
    }

}