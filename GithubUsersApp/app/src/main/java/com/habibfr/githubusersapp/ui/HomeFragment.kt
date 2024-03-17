package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.Result
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())

        homeViewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[HomeViewModel::class.java]

        val layoutManager = LinearLayoutManager(activity)

        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        binding.searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_favorit -> {
                    val favUserFragment = FavoriteUserFragment()
                    val fragmentManager = parentFragmentManager

                    fragmentManager.beginTransaction().apply {
                        replace(
                            R.id.frame_container,
                            favUserFragment,
                            FavoriteUserFragment::class.java.simpleName
                        )
                        addToBackStack(null)
                        commit()
                    }
                }

                R.id.menu_setting -> {
                    val settingFragment = SettingFragment()
                    val fragmentManager = parentFragmentManager

                    fragmentManager.beginTransaction().apply {
                        replace(
                            R.id.frame_container,
                            settingFragment,
                            SettingFragment::class.java.simpleName
                        )
                        addToBackStack(null)
                        commit()
                    }
                }
            }

            true
        }

//        binding.searchBar.setMenu(R.menu.option_menu)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    val query = binding.searchView.text.toString().trim()
                    homeViewModel.getUsers(query)
                    false
                }
        }



        homeViewModel.getUsers().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        setUser(newsData)
//                        newsAdapter.submitList(newsData)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            context, "Terjadi kesalahan" + result.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setUser(items: List<FavoriteUser?>?) {
        val favUserAdapter = UserAdapter { user ->
            if (user.isBookmarked) {
                homeViewModel.deleteFavoriteUser(user)
            } else {
                homeViewModel.saveFavoriteUser(user)
            }
        }
        favUserAdapter.submitList(items)
        binding.rvUser.adapter = favUserAdapter

        favUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

            override fun onItemClicked(user: FavoriteUser) {
                showSelectedUser(user)
                user.username.let { homeViewModel.selectUser(it) }
            }

        })
    }

    private fun showSelectedUser(user: FavoriteUser) {
        val bundle = Bundle()
        bundle.putString(DetailFragment.USERNAME, user.username)

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        val fragmentManager = parentFragmentManager

        fragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, detailFragment, DetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
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