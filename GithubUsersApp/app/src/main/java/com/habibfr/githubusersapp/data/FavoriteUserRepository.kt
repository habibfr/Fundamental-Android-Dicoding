package com.habibfr.githubusersapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.habibfr.githubusersapp.BuildConfig
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.data.local.room.FavoriteUserDao
import com.habibfr.githubusersapp.data.response.GithubResponse
import com.habibfr.githubusersapp.data.response.Users
import com.habibfr.githubusersapp.data.retrofit.ApiService
import com.habibfr.githubusersapp.ui.HomeViewModel
import com.habibfr.githubusersapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()

    fun getUsers(username: String): LiveData<Result<List<FavoriteUser>>> {
        result.value = Result.Loading
        val client = apiService.getUsers(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    val users = response.body()?.items
                    val favUsers = ArrayList<FavoriteUser>()
                    appExecutors.diskIO.execute {
                        users?.forEach { user ->
                            val isBookmarked = favUserDao.isNewsBookmarked(user.login)
                            val favUser = FavoriteUser(
                                user.login,
                                user.avatarUrl,
                                isBookmarked
                            )
                            favUsers.add(favUser)
                        }
                        favUserDao.deleteAll()
                        favUserDao.insertFavoriteUser(favUsers)
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = favUserDao.geFavoriteUser()
        result.addSource(localData) { newData: List<FavoriteUser> ->
            result.value = Result.Success(newData)
        }
        return result
    }

//    fun searchUsers(username: String): LiveData<Result<List<Users>>> {
//        val result = MutableLiveData<Result<List<Users>>>()
//        result.value = Result.Loading
//        val client = apiService.getUsers(username)
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
//                if (response.isSuccessful) {
//                    val users = response.body()?.items
//                    result.value = Result.Success(users ?: emptyList())
//                } else {
//                    result.value = Result.Error("Error: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//        return result
//    }

    fun getBookmarkedFavoriteUser(): LiveData<List<FavoriteUser>> {
        return favUserDao.getBookmarkedFavoriteUser()
    }

    fun searchUserFav(username: String): LiveData<List<FavoriteUser>> {
        return favUserDao.searchUserFav(username)
    }

    fun setBookmarkFavoriteUser(favUser: FavoriteUser, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            favUser.isBookmarked = bookmarkState
            favUserDao.updateFavoriteUser(favUser)
        }
    }


    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, favUserDao, appExecutors)
            }.also { instance = it }
    }

}