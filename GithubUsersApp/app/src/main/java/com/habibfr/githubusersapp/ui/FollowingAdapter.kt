package com.habibfr.githubusersapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibfr.githubusersapp.data.response.UserFollowingItem
import com.habibfr.githubusersapp.databinding.ItemFollowingBinding

class FollowingAdapter :
    ListAdapter<UserFollowingItem, FollowingAdapter.FollowingViewHolder>(DIFF_CALLBACK) {
    class FollowingViewHolder(val binding: ItemFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(following: UserFollowingItem) {
            Glide.with(itemView.context).load(following.avatarUrl).into(binding.imgAvatarFollowing)
            binding.txtUsernameFollowing.text = following.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding =
            ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val following = getItem(position)
        holder.bind(following)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowingItem>() {

            override fun areItemsTheSame(
                oldItem: UserFollowingItem,
                newItem: UserFollowingItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowingItem,
                newItem: UserFollowingItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}