package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.data.response.UserFollowerItem
import com.habibfr.githubusersapp.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)
        binding.rvFollower.setHasFixedSize(true);

        val homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        val followerViewModel = ViewModelProvider(requireActivity())[FollowerViewModel::class.java]

        val user = homeViewModel.selectedUser.value
        followerViewModel.follower.observe(viewLifecycleOwner) { follower ->
            if (follower != null) {
                showLoading(false)
            } else {
                showLoading(true)
                user?.let {
                    followerViewModel.findFollower(it)
                }
            }
            setFollower(follower)
        }

        followerViewModel.isLoadingFollower.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.selectedUser.observe(viewLifecycleOwner) { username ->
            if (!followerViewModel.isFollowerLoaded || followerViewModel.currentUsername != username) {
                followerViewModel.findFollower(username)
                followerViewModel.currentUsername = username
            }
        }
    }

    private fun setFollower(userFollower: List<UserFollowerItem?>?) {
        val adapterFollower = FollowerAdapter()

        if (userFollower?.isEmpty() == true) {
            binding.tvFollowerKosong.visibility = View.VISIBLE
        } else {
            binding.tvFollowerKosong.visibility = View.GONE
        }


        adapterFollower.submitList(userFollower)
        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(activity)

            setHasFixedSize(true)
            adapter = adapterFollower
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollower.visibility = View.VISIBLE
        } else {
            binding.progressBarFollower.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}