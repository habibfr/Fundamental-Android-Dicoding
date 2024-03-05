package com.habibfr.restaurantreview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.habibfr.restaurantreview.R
import com.habibfr.restaurantreview.data.response.CustomerReviewsItem
import com.habibfr.restaurantreview.data.response.Restaurant
import com.habibfr.restaurantreview.data.response.RestaurantResponse
import com.habibfr.restaurantreview.data.retrofit.ApiConfig
import com.habibfr.restaurantreview.databinding.ActivityMainBinding
import com.habibfr.restaurantreview.databinding.ItemReviewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        findRestaurant()
    }

    private fun findRestaurant() {
        showLoading(true)

        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>, response: Response<RestaurantResponse>
            ) {
                showLoading(false)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant?.customerReviews)
                    } else {
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    private fun setReviewData(customerReviews: List<CustomerReviewsItem?>?) {
        val adapter = ReviewAdapter()
        adapter.submitList(customerReviews)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")

    }

    private fun setRestaurantData(restaurant: Restaurant?) {
        binding.tvTitle.text = restaurant?.name
        binding.tvDescription.text = restaurant?.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant?.pictureId}")
            .into(binding.ivPicture)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}