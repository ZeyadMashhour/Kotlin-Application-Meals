package com.example.mymeals.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.mymeals.R
import com.example.mymeals.databinding.ActivityMainBinding
import com.example.mymeals.presentation.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewMvvm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize your MainViewModel using ViewModelProvider
        mainViewMvvm = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewMvvm.getTenRandomMeals()
        observeRandomMeal()
    }

    private fun observeRandomMeal() {
        mainViewMvvm.observeTenRandomMealsLiveData().observe(this) { randomMeals ->
            Log.d("Main activity random meals", randomMeals.toString())
        }
    }
}
