package com.example.mymeals.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mymeals.R
import com.example.mymeals.presentation.ui.fragments.HomeFragment
import com.example.mymeals.presentation.viewmodels.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var MealMvvm: MealViewModel
    private lateinit var 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        var mealId = intent.getStringExtra("MEAL_ID")!!

        MealMvvm.getMealDetailsById(mealId)
    }


    private fun observeRandomMeal() {
        MealMvvm.observeMealDetailsLiveData().observe(this) { meal ->

        }
    }
}