package com.example.mymeals.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymeals.data.model.Meal
import com.example.mymeals.databinding.ActivityMealBinding
import com.example.mymeals.presentation.viewmodels.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var MealMvvm: MealViewModel
    private lateinit var mealDetails: Meal
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        val mealId = intent.getStringExtra("MEAL_ID")!!
        Log.d("Meal id", mealId)

        MealMvvm.getMealDetailsById(mealId.toInt())
        observeRandomMeal()

    }


    private fun observeRandomMeal() {
        MealMvvm.observeMealDetailsLiveData().observe(this) { meal ->
            mealDetails = meal
            Log.d("Meal Details in activity", mealDetails.toString())
            setupLayoutWithMealDetails()
        }
    }

    private fun setupLayoutWithMealDetails(){
        binding.tvCategoryInfo.text = "Category : ${mealDetails.strCategory}"
        binding.tvAreaInfo.text = "Area: ${mealDetails.strArea}"
        binding.tvInstructions.text = mealDetails.strInstructions
        youtubeLink = mealDetails.strYoutube
        binding.collapsingToolbar.title = mealDetails.strMeal
        Glide.with(applicationContext)
            .load(mealDetails.strMealThumb)
            .into(binding.imgMealDetail)
    }
}