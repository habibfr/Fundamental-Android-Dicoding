package com.habibfr.githubusersapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.habibfr.githubusersapp.R

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.databinding.ItemUserBinding

class FavoriteUserAdapter(private val onFavUserClick: (FavoriteUser) -> Unit) :
    ListAdapter<FavoriteUser, FavoriteUserAdapter.FavUserViewHolder>(DIFF_CALLBACK) {
    inner class FavUserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavoriteUser) {
            binding.txtUsername.text = favUser.username
            Glide.with(itemView.context)
                .load(favUser.avatarUrl)
//                .apply(
//                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
//                )
                .into(binding.imgAvatar)
//            itemView.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse(news.url)
//                itemView.context.startActivity(intent)
//            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUser> =
            object : DiffUtil.ItemCallback<FavoriteUser>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteUser,
                    newItem: FavoriteUser
                ): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: FavoriteUser,
                    newItem: FavoriteUser
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        val favUser = getItem(position)
        holder.bind(favUser)
        val ivBookmark = holder.binding.imgFav

        if (favUser.isBookmarked) {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.baseline_favorite_24
                )
            )
        } else {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.baseline_favorite_border_24
                )
            )
        }
        ivBookmark.setOnClickListener {
            onFavUserClick(favUser)
        }
    }
}