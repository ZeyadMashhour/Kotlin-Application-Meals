package com.example.mymeals.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymeals.R
import com.example.mymeals.data.model.Meal
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
        onPopularItemClick()
        onCategoryItemClick()


        binding.imgSearch.setOnClickListener {
            val meal = binding.etSearch.text.toString().trim() 

            if (meal.isNotEmpty()) {
                mainViewMvvm.searchMeal(meal)
                observeSearchMealLiveData()
            } else {
                Toast.makeText(context, getString(R.string.search_bar_error), Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick = { category ->
            mainViewMvvm.showCategoryMeals(category.strCategory)
            val categoryName = category.strCategory
            val dialogFragment = CategoryMealsDialogFragment.newInstance(categoryName)
            dialogFragment.show(parentFragmentManager, "category_meals_dialog")
        }
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
            startMealActivity(meal)
        }
    }


    private fun prepareCategoriesRecyclerView() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        mainViewMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun observeSearchMealLiveData() {
        mainViewMvvm.observeMealSearchLiveData().observe(viewLifecycleOwner) { meals ->
            if (meals.meals != null){
                startMealActivity(meals.meals[0])
            }else
                Toast.makeText(context, "The meal doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }
    private fun startMealActivity(meal: Meal){
        val intent = Intent(activity, MealActivity::class.java)
        intent.putExtra("MEAL_ID", meal.idMeal)
        startActivity(intent)
    }

}
