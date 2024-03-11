package com.habibfr.githubusersapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibfr.githubusersapp.data.response.UserFollowerItem
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _follower = MutableLiveData<List<UserFollowerItem?>?>()
    val follower: LiveData<List<UserFollowerItem?>?> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoading

    var isFollowerLoaded: Boolean = false
        private set
    var currentUsername: String? = null

    companion object {
        private const val TAG = "FollowerFragment"
    }

    fun findFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollower(
            username
        )
        client.enqueue(object : Callback<List<UserFollowerItem>> {
            override fun onResponse(
                call: Call<List<UserFollowerItem>>, response: Response<List<UserFollowerItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follower.value = responseBody
                        isFollowerLoaded = true
                    }
                } else {
                    Log.e(TAG, "onFailure res : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowerItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure get follower: ${t.message}")
            }
        })
        currentUsername = username
    }
}