package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.DetailUser
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import com.habibfr.githubusersapp.databinding.FragmentDetailBinding
import retrofit2.*

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel


    companion object {
        private val TAB_TITLES = arrayOf(
            "Follower", "Following"
        )
        const val USERNAME = "username"
        const val TAG = "DetailUserActivity"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity() as AppCompatActivity)

        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]

        detailViewModel.detailUser.observe(viewLifecycleOwner) { detailUser ->
            setUserData(detailUser)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


//        if (arguments != null) {
//            val username = arguments?.getString(USERNAME)
//            if (username != null) {
//                detailUser(username)
//                Log.d("tes username", username)
//
//
//                val bundle = Bundle()
//                bundle.putString(FollowerFragment.USERNAME, username)
//
//                val followerFragment = FollowerFragment()
//                followerFragment.arguments = bundle
//                val fragmentManager = parentFragmentManager
//
//                fragmentManager.beginTransaction().apply {
//                    replace(
//                        R.id.frame_view_pager_container,
//                        followerFragment,
//                        FollowerFragment::class.java.simpleName
//                    ).commit()
//                }
//            }
//
//        }

        sharedViewModel.selectedItem.observe(viewLifecycleOwner) { username ->
            detailViewModel.detailUser(username)
        }
    }


//    private fun detailUser(username: String) {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getDetailUser(
//            username,
//            "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"
//        )
//        client.enqueue(object : Callback<DetailUser> {
//            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUserData(responseBody)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure Res: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
//                Log.e(TAG, "onFailure GET: ${t.message}")
//
//            }
//
//        })
//    }

    private fun setUserData(detailUser: DetailUser?) {
        binding.tvUsername.text = detailUser?.login
        binding.tvName.text = detailUser?.name
        activity?.let {
            Glide.with(it)
                .load(detailUser?.avatarUrl)
                .into(binding.imgAvatar)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}