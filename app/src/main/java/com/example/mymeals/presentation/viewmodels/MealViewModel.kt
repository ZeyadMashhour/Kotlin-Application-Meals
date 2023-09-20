package com.example.mymeals.presentation.viewmodels

import android.util.Log
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

    fun getMealDetailsById(id: Int){
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.getMealDetailsById(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() !=null){
                    val mealDetails: MealList = response.body()!!
                    mealDetailLiveData.value = mealDetails.meals[0]
                    Log.d("Meal Details", mealDetails.toString())
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Meal Details", t.message.toString())
            }

        })
    }
    fun observeMealDetailsLiveData(): MutableLiveData<Meal> {
        return mealDetailLiveData
    }
}