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
import com.example.mymeals.presentation.adapters.CategoriesAdapter
import com.example.mymeals.presentation.adapters.RandomMealsAdapter
import com.example.mymeals.presentation.ui.activities.MealActivity
import com.example.mymeals.presentation.ui.viewmodels.MainViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewMvvm: MainViewModel
    private lateinit var randomMealsAdapter: RandomMealsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewMvvm = ViewModelProvider(this)[MainViewModel::class.java]
        randomMealsAdapter = RandomMealsAdapter()
        categoriesAdapter = CategoriesAdapter()
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

        prepareRandomMealsRecyclerView()
        prepareCategoriesRecyclerView()
        mainViewMvvm.getTenRandomMeals()
        observeRandomMeal()
        mainViewMvvm.getCategories()
        observeCategoriesLiveData()
        mainViewMvvm.getCuisinesList()
        onPopularItemClick()


    }




    private fun prepareRandomMealsRecyclerView() {
        binding.rvRandomMeals.apply {
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL,false)
            adapter = randomMealsAdapter
        }
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

    private fun prepareCategoriesRecyclerView() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        // Make sure that observeCategoriesLiveData() returns LiveData in your MainViewModel
        mainViewMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            // Update your UI with the observed categories here using categoriesAdapter
            categoriesAdapter.setCategoryList(categories)
        }
    }

}
