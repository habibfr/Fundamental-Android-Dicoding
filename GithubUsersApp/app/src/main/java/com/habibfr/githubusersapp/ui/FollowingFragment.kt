package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.githubusersapp.data.response.UserFollowingItem
import com.habibfr.githubusersapp.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)
        binding.rvFollowing.setHasFixedSize(true)

        val homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        val followingViewModel =
            ViewModelProvider(requireActivity())[FollowingViewModel::class.java]

        val user = homeViewModel.selectedUser.value
        followingViewModel.following.observe(viewLifecycleOwner) { following ->
            if (following != null) {
                showLoading(false)
            } else {
                showLoading(true)
                user?.let {
                    followingViewModel.findFollowing(it)
                }
            }
            setFollowing(following)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.selectedUser.observe(viewLifecycleOwner) { username ->
            if (!followingViewModel.isFollowingLoaded || followingViewModel.currentUsername != username) {
                followingViewModel.findFollowing(username)
                followingViewModel.currentUsername = username
            }
        }
    }

    private fun setFollowing(userFollowing: List<UserFollowingItem?>?) {

        if (userFollowing?.isEmpty() == true) {
            binding.tvFollowingKosong.visibility = View.VISIBLE
        } else {
            binding.tvFollowingKosong.visibility = View.GONE
        }

        val adapter = FollowingAdapter()
        adapter.submitList(userFollowing)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}