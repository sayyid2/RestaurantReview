package com.sayyid.restaurantreview.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sayyid.restaurantreview.R
import com.sayyid.restaurantreview.data.response.CustomerReviewsItem
import com.sayyid.restaurantreview.data.response.PostReviewResponse
import com.sayyid.restaurantreview.data.response.Restaurant
import com.sayyid.restaurantreview.data.response.RestaurantResponse
import com.sayyid.restaurantreview.data.retrofit.ApiConfig
import com.sayyid.restaurantreview.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    companion object{
        private const val TAG="MainActivity"
        private const val RESTAURANT_ID="uewq1zg2zlskfw1e867"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.restaurant.observe(this){
            restaurant->setRestaurantData(restaurant)
        }

        val layoutManager=LinearLayoutManager(this)
        binding.rvReview.layoutManager=layoutManager
        val itemDecoration=DividerItemDecoration(this,layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this){consumerReview->
            setReviewData(consumerReview)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

//        findRestaurant()
        binding.btnSend.setOnClickListener{
            view->
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }





    private fun setRestaurantData(restaurant: Restaurant){
        binding.tvTitle.text=restaurant.name
        binding.tvDescription.text=restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }
    
    private fun setReviewData(consumerReviews:List<CustomerReviewsItem>){
        val adapter= ReviewAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter=adapter
        binding.edReview.setText("")
    }
    
    private fun showLoading(isLoding:Boolean){
        if (isLoding){
            binding.progressBar.visibility=View.VISIBLE
        }else{
            binding.progressBar.visibility=View.GONE
        }
    }

}


