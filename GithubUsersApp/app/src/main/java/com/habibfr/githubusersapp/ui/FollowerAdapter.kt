package com.habibfr.githubusersapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habibfr.githubusersapp.data.response.UserFollowerItem
import com.habibfr.githubusersapp.databinding.ItemFollowerBinding

class FollowerAdapter : ListAdapter<UserFollowerItem, FollowerAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemFollowerBinding) : RecyclerView.ViewHolder(binding.root)  {
        fun bind(followerItem: UserFollowerItem){
            Glide.with(itemView.context).load(followerItem.avatarUrl).into(binding.imgAvatarFollower)
            binding.txtUsernameFollower.text = followerItem.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val followerItem = getItem(position)
        holder.bind(followerItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowerItem>() {
            override fun areItemsTheSame(oldItem: UserFollowerItem, newItem: UserFollowerItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserFollowerItem, newItem: UserFollowerItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}