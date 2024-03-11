package com.habibfr.githubusersapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.habibfr.githubusersapp.data.response.DetailUser
import com.habibfr.githubusersapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val TAB_TITLES = arrayOf(
            "Follower [0]",
            "Following [0]"
        )
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        val homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        homeViewModel.selectedUser.observe(viewLifecycleOwner) { username ->
            if (!detailViewModel.isDetailLoaded || detailViewModel.currentUsername != username) {
                detailViewModel.detailUser(username)
                detailViewModel.currentUsername = username
            }
        }

        val user = homeViewModel.selectedUser.value

        detailViewModel.detailUser.observe(viewLifecycleOwner) { detailUser ->
            if (detailUser != null) {
                showLoading(false)
            } else {
                showLoading(true)
                user?.let {
                    detailViewModel.detailUser(it)
                }
            }
            setUserData(detailUser)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setUserData(detailUser: DetailUser?) {
        binding.tvUsername.text = detailUser?.login
        binding.tvName.text = detailUser?.name

        val followerTab = binding.tabs.getTabAt(0)
        followerTab?.text = String.format("Follower [%d]", detailUser?.followers)

        val followingTab = binding.tabs.getTabAt(1)
        followingTab?.text = String.format("Following [%d]", detailUser?.following)

        activity?.let {
            Glide.with(it).load(detailUser?.avatarUrl).into(binding.imgAvatar)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}