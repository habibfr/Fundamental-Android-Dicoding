package com.habibfr.githubusersapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibfr.githubusersapp.data.response.DetailUser
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUser?>()
    val detailUser: LiveData<DetailUser?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var isDetailLoaded: Boolean = false
        private set
    var currentUsername: String? = null

    companion object {
        private const val TAG = "DetailFragment"
    }


    fun detailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(
            username
        )
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                        isDetailLoaded = true
                    }
                } else {
                    Log.e(TAG, "onFailure Res: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure GET: ${t.message}")
            }
        })

        currentUsername = username
    }
}