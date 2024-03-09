package com.habibfr.githubusersapp.data.retrofit

import com.habibfr.githubusersapp.data.response.DetailUser
import com.habibfr.githubusersapp.data.response.GithubResponse
import com.habibfr.githubusersapp.data.response.UserFollower
import com.habibfr.githubusersapp.data.response.UserFollowerItem
import com.habibfr.githubusersapp.data.response.UserFollowing
import com.habibfr.githubusersapp.data.response.UserFollowingItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") username: String,
        @Header("Authorization") token: String
    ): Call<GithubResponse>

    @GET("/users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<DetailUser>


    @GET("/users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserFollowerItem>>

    @GET("/users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserFollowingItem>>

}
