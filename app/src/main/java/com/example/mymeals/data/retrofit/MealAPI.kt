package com.example.mymeals.data.retrofit

import com.example.mymeals.data.model.CategoryList
import com.example.mymeals.data.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealAPI {

    @GET("randomselection.php")
    fun getTenRandomMeals(): Call<MealList>
    @GET("lookup.php")
    fun getMealDetailsById(@Query("i") id: Int): Call<MealList>

    @GET("categories.php")
    fun getCategoriesList(): Call<CategoryList>

}