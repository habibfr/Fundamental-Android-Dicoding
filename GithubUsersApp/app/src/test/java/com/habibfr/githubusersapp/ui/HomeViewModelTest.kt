package com.habibfr.githubusersapp.ui

import com.habibfr.githubusersapp.data.FavoriteUserRepository
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.data.local.room.FavoriteUserDao
import com.habibfr.githubusersapp.data.retrofit.ApiService
import com.habibfr.githubusersapp.utils.AppExecutors
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @Mock
    private lateinit var favoriteUserRepository: FavoriteUserRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        favoriteUserRepository = mock(FavoriteUserRepository::class.java)
        homeViewModel = HomeViewModel(favoriteUserRepository)
    }

    @Test
    fun testSaveFavoriteUser() {
        val favUser = FavoriteUser("habibfr", "https://example.com/avatar.jpg", false)
        homeViewModel.saveFavoriteUser(favUser)
        verify(favoriteUserRepository, times(1)).setBookmarkFavoriteUser(favUser, true)
    }
}