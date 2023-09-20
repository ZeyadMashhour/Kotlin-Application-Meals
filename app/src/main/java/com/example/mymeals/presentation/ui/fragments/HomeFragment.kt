package com.example.mymeals.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymeals.databinding.FragmentHomeBinding
import com.example.mymeals.presentation.adapters.RandomMealsAdapter
import com.example.mymeals.presentation.ui.activities.MealActivity
import com.example.mymeals.presentation.viewmodels.MainViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewMvvm: MainViewModel
    private lateinit var randomMealsAdapter: RandomMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewMvvm = ViewModelProvider(this)[MainViewModel::class.java]
        randomMealsAdapter = RandomMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Find the RecyclerView from the layout
        val recyclerView = binding.rvRandomMeals

        // Set the layout manager for the RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)

        // Set the adapter for the RecyclerView
        recyclerView.adapter = randomMealsAdapter
        mainViewMvvm.getTenRandomMeals()
        observeRandomMeal()
        onPopularItemClick()
    }


    private fun observeRandomMeal() {
        mainViewMvvm.observeTenRandomMealsLiveData().observe(viewLifecycleOwner) { randomMeals ->
            randomMealsAdapter.setMeals(randomMeals)
        }
    }
    private fun onPopularItemClick() {
        randomMealsAdapter.onItemClick = {meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra("MEAL_ID", meal.idMeal)
            Log.d("Test", "$meal")
            startActivity(intent)
        }
    }

}
