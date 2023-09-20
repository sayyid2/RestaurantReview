package com.sayyid.restaurantreview.data.retrofit

import com.sayyid.restaurantreview.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ):Call<RestaurantResponse>
}