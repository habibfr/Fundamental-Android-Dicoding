package com.habibfr.githubusersapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.githubusersapp.R
import com.habibfr.githubusersapp.data.Result
import com.habibfr.githubusersapp.data.local.entity.FavoriteUser
import com.habibfr.githubusersapp.databinding.FragmentFavoriteUserBinding
import com.habibfr.githubusersapp.databinding.FragmentHomeBinding

class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
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

        binding.rvUserFav.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvUserFav.addItemDecoration(itemDecoration)

        with(binding) {
            searchViewFav.setupWithSearchBar(searchBarFav)
            searchViewFav
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchViewFav.hide()
                    val query = binding.searchViewFav.text.toString().trim()
                    homeViewModel.searchUserFav(query).observe(viewLifecycleOwner) { userFav ->
                        if (userFav.isEmpty()) {
                            homeViewModel.getFavUsers()
                                .observe(viewLifecycleOwner) { favUser ->
                                    setUser(favUser)
                                }
                        } else {
                            setSearchUser(userFav)
                        }
                    }

                    false
                }
        }

        homeViewModel.getFavUsers().observe(viewLifecycleOwner) { favUser ->
            setUser(favUser)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


    }

    private fun setSearchUser(items: List<FavoriteUser?>?) {
        val favUserAdapter = UserAdapter { user ->
            if (user.isBookmarked) {
                homeViewModel.deleteFavoriteUser(user)
            } else {
                homeViewModel.saveFavoriteUser(user)
            }
        }
        favUserAdapter.submitList(items)
        binding.rvUserFav.adapter = favUserAdapter

        favUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

            override fun onItemClicked(user: FavoriteUser) {
                showSelectedUser(user)
                user.username.let { homeViewModel.selectUser(it) }
            }

        })
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
        binding.rvUserFav.adapter = favUserAdapter

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
            binding.progressBarFav.visibility = View.VISIBLE
        } else {
            binding.progressBarFav.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}