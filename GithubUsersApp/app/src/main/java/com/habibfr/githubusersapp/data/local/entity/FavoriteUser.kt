package com.habibfr.githubusersapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_user")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)