package com.example.mymeals.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymeals.R
import com.example.mymeals.data.database.DatabaseHandler
import com.example.mymeals.databinding.FragmentFavoriteBinding
import com.example.mymeals.presentation.adapters.FavoriteMealsAdapter
import com.example.mymeals.presentation.ui.activities.MealActivity


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteMealsAdapter = FavoriteMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.rvFavoriteMeals
        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)
        recyclerView.adapter = favoriteMealsAdapter
        setupMealsInTheAdapter()
        onPopularItemClick()
    }

    private fun setupMealsInTheAdapter(){
        val db = DatabaseHandler(requireContext())
        val favoriteMeals = db.getMealsFromDatabase()
        Log.d("favorite meals" , favoriteMeals.toString())
        favoriteMealsAdapter.setMeals(favoriteMeals)
    }

    private fun onPopularItemClick() {
        favoriteMealsAdapter.onItemClick = {meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra("MEAL_ID", meal.idMeal)
            Log.d("Test", "$meal")
            startActivity(intent)
        }
    }


}