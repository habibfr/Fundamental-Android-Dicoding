package com.habibfr.githubusersapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM fav_user")
    fun geFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM fav_user where bookmarked = 1")
    fun getBookmarkedFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM fav_user where bookmarked = 1 AND username = :user")
    fun searchUserFav(user: String): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favUser: List<FavoriteUser>)

    @Update
    fun updateFavoriteUser(favUser: FavoriteUser)

    @Query("DELETE FROM fav_user WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM fav_user WHERE username = :title AND bookmarked = 1)")
    fun isNewsBookmarked(title: String): Boolean
}