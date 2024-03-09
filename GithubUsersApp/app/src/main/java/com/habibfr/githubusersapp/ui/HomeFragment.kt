package com.habibfr.githubusersapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.response.GithubResponse
import com.habibfr.githubusersapp.data.response.Users
import com.habibfr.githubusersapp.data.retrofit.ApiConfig
import com.habibfr.githubusersapp.databinding.FragmentDetailBinding
import com.habibfr.githubusersapp.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val homeViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        homeViewModel.users.observe(viewLifecycleOwner) { users ->
            setUser(users)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(activity)

        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    rvUser.visibility = View.GONE

                    val query = binding.searchView.text.toString().trim()
                    homeViewModel.findUsers(query)
                    rvUser.visibility = View.VISIBLE

                    false
                }
        }

//        findUsers()


    }

//
//    private fun findUsers(username: String = "habibfr") {
//        showLoading(true)
//        val client = ApiConfig.getApiService().getUsers(
//            username,
//            "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"
//        )
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(
//                call: Call<GithubResponse>,
//                response: Response<GithubResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setUser(responseBody.items)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    private fun setUser(items: List<Users?>?) {
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: Users) {
                showSelectedUser(user)
                user.login?.let { sharedViewModel.selectItem(it) }
            }

        })
    }

    private fun showSelectedUser(user: Users) {
        val bundle = Bundle()
        bundle.putString(DetailFragment.USERNAME, user.login)

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        val fragmentManager = parentFragmentManager

        fragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, detailFragment, DetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }

//        Toast.makeText(activity, user.login, Toast.LENGTH_SHORT).show()
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

    companion object {
        const val TAG = "HOME FRAGMENT"
    }
}