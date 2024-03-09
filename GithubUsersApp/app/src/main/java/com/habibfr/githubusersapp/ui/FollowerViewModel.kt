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
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowerFragment"
    }

    fun findFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollower(
            username,
            "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"
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
                    }
                } else {
                    Log.e(TAG, "onFailure follower response : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowerItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure get follower: ${t.message}")
            }
        })
    }
}