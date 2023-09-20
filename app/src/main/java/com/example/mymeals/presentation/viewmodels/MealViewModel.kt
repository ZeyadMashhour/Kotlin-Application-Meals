package com.example.mymeals.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymeals.data.model.Meal
import com.example.mymeals.data.model.MealList
import com.example.mymeals.data.retrofit.MealAPI
import com.example.mymeals.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel: ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetailsById(id:String){
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.getMealDetailsById(id).enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if(response.body() !=null){
                    val mealDetails: Meal = response.body()!!
                    mealDetailLiveData.value = mealDetails
                    Log.d("Meal Details", mealDetails.toString())
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Log.d("Meal Details", t.message.toString())
            }

        })
    }
    fun observeMealDetailsLiveData(): MutableLiveData<Meal> {
        return mealDetailLiveData
    }
}