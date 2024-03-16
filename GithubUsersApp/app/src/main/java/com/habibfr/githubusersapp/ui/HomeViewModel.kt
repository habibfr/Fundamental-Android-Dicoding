package com.habibfr.githubusersapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibfr.githubusersapp.data.FavoriteUserRepository
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.data.response.GithubResponse
import com.habibfr.githubusersapp.data.response.Users
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {
    private val _users = MutableLiveData<List<FavoriteUser?>?>()
    val users: LiveData<List<FavoriteUser?>?> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _selectedUser = MutableLiveData<String>()
    val selectedUser: LiveData<String> get() = _selectedUser

    fun selectUser(username: String) {
        _selectedUser.value = username
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    init {
        getUsers()
    }


//    fun findUsers(username: String = "habibfr") {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getUsers(
//            username
//        )
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(
//                call: Call<GithubResponse>, response: Response<GithubResponse>
//            ) {
//                _isLoading.value = false
//
////                if (response.isSuccessful) {
////                    val responseBody = response.body()
////                    if (responseBody != null) {
////                        _users.value = responseBody.items
////                    }
////                } else {
////                    Log.e(TAG, "onFailure: ${response.message()}")
////                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    fun getUsers(username: String = "habibfr") = favoriteUserRepository.getUsers(username)
    fun getFavUsers() = favoriteUserRepository.getBookmarkedFavoriteUser()
//    fun searchUsers(username: String) = favoriteUserRepository.searchUsers(username)
    fun searchUserFav(username: String) = favoriteUserRepository.searchUserFav(username)



//    fun getBookmarkedNews() = favoriteUserRepository.getBookmarkedFavoriteUser()

    fun saveFavoriteUser(favUser: FavoriteUser) {
        favoriteUserRepository.setBookmarkFavoriteUser(favUser, true)
    }
    fun deleteFavoriteUser(favUser: FavoriteUser) {
        favoriteUserRepository.setBookmarkFavoriteUser(favUser, false)
    }

}