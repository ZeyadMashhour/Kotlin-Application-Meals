package com.example.mymeals.presentation.ui.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymeals.data.model.AreaList
import com.example.mymeals.data.model.Category
import com.example.mymeals.data.model.CategoryList
import com.example.mymeals.data.model.MealList
import com.example.mymeals.data.retrofit.MealAPI
import com.example.mymeals.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private var randomMealsLiveData = MutableLiveData<MealList>()
    private var categoriesLiveData = MutableLiveData<CategoryList>()
    private var mealSearchLiveData = MutableLiveData<MealList>()

    fun getTenRandomMeals(){
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.getTenRandomMeals().enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() !=null){
                    val randomMeals:MealList = response.body()!!
                    randomMealsLiveData.value = randomMeals
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
        mealsApi.getCategoriesList().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    categoriesLiveData.value = response.body()!!
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun observeCategoriesLiveData():LiveData<CategoryList>{
        return categoriesLiveData
    }

    fun searchMeal(meal: String) {
        val mealsApi = RetrofitInstance.instance.create(MealAPI::class.java)
        mealsApi.searchMeal(meal).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        mealSearchLiveData.value = response.body()
                        Log.d("MainViewModel", "Searched meals received: ${response.body()!!.meals}")
                    } else {
                        null.also { mealSearchLiveData.value = it }
                        Log.d("MainViewModel", "Response body is null")
                    }
                } else {
                    Log.d("MainViewModel", "API response is not successful")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainViewModel", "API call failed: ${t.message}")
            }
        })
    }
    fun observeMealSearchLiveData(): LiveData<MealList> {
        return mealSearchLiveData
    }

}