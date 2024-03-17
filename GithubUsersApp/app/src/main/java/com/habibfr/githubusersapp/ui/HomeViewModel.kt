package com.habibfr.githubusersapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habibfr.githubusersapp.data.FavoriteUserRepository
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser

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

    fun getUsers(username: String = "habibfr") = favoriteUserRepository.getUsers(username)

    fun getFavUsers() = favoriteUserRepository.getBookmarkedFavoriteUser()
    fun searchUserFav(username: String) = favoriteUserRepository.searchUserFav(username)
    fun saveFavoriteUser(favUser: FavoriteUser) {
        favoriteUserRepository.setBookmarkFavoriteUser(favUser, true)
    }

    fun deleteFavoriteUser(favUser: FavoriteUser) {
        favoriteUserRepository.setBookmarkFavoriteUser(favUser, false)
    }

}