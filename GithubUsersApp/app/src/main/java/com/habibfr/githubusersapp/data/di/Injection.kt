package com.habibfr.githubusersapp.data.di

import android.content.Context
import com.habibfr.githubusersapp.data.FavoriteUserRepository
import com.habibfr.githubusersapp.data.local.room.FavoriteUserDatabase
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import com.habibfr.githubusersapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(apiService, dao, appExecutors)
    }
}