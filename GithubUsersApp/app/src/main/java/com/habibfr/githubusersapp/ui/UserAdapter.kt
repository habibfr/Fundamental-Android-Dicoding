package com.habibfr.githubusersapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.databinding.ItemUserBinding

class UserAdapter(private val onFavUserClick: (FavoriteUser) -> Unit) :
    ListAdapter<FavoriteUser, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavoriteUser) {
            Glide.with(itemView.context).load(favUser.avatarUrl).into(binding.imgAvatar)
            binding.txtUsername.text = favUser.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(users) }
        holder.bind(users)
        val ivBookmark = holder.binding.imgFav

        if (users.isBookmarked) {
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
            onFavUserClick(users)
        }
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: FavoriteUser)
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
}