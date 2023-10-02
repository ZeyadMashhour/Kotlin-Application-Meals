package com.example.mymeals.presentation.ui.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymeals.data.model.Categories
import com.example.mymeals.data.model.MealList
import com.example.mymeals.data.retrofit.MealAPI
import com.example.mymeals.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private var randomMealsLiveData = MutableLiveData<MealList>()
    private var categoriesLiveData = MutableLiveData<Categories>()

    fun getTenRandomMeals(){
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.getTenRandomMeals().enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() !=null){
                    val randomMeals:MealList = response.body()!!
                    randomMealsLiveData.value = randomMeals
                    Log.d("Random Fragments top 10 random meals", randomMeals.toString())
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Random Fragments top 10 random meals", t.message.toString())
            }

        })
    }
    fun observeTenRandomMealsLiveData():LiveData<MealList>{
        return randomMealsLiveData
    }

    fun getCategories(){
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.getCategoriesList().enqueue(object : Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                if(response.body() !=null){
                    val categories:Categories = response.body()!!
                    categoriesLiveData.value = categories
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.d("Categories", t.message.toString())
            }

        })
    }

    fun observeCategoriesLiveData():LiveData<Categories>{
        return observeCategoriesLiveData()
    }
}