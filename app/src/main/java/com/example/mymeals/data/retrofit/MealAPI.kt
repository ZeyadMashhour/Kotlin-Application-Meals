package com.example.mymeals.data.retrofit

import com.example.mymeals.data.model.MealList
import retrofit2.Call
import retrofit2.http.GET


interface MealAPI {

    @GET("randomselection.php")
    fun getTenRandomMeals(): Call<MealList>

}