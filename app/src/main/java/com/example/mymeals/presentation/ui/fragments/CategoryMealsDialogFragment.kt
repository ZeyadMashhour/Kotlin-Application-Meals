package com.example.mymeals.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mymeals.databinding.FragmentCategoryMealsDialogBinding
import com.example.mymeals.presentation.adapters.CategoryMealsAdapter
import com.example.mymeals.presentation.ui.activities.MealActivity
import com.example.mymeals.presentation.ui.viewmodels.MainViewModel

class CategoryMealsDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCategoryMealsDialogBinding
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private lateinit var mainViewMvvm:MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryMealsDialogBinding.inflate(inflater, container, false)
        mainViewMvvm = ViewModelProvider(this)[MainViewModel::class.java]

        categoryMealsAdapter = CategoryMealsAdapter()
        prepareCategoriesRecyclerView()
        onCategoryMealsItemClick()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryName = arguments?.getString(CATEGORY_NAME)

        // Check if categoryName is not null and call showCategoryMeals
        categoryName?.let { category ->
            showCategoryMeals(category)
        }

        Log.d("observer mainvew", "view created")

    }



    private fun prepareCategoriesRecyclerView() {
        binding.rvCategoryMeal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
    private fun onCategoryMealsItemClick() {
        categoryMealsAdapter.onItemClick = {meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra("MEAL_ID", meal.idMeal)
            startActivity(intent)
        }
    }

    private fun observeCategoryMealLiveData() {
        mainViewMvvm.observeCategoryMealsLiveData().observe(viewLifecycleOwner){meals ->
            Log.d("observer", "Hello world")
            Log.d("observer", meals.toString())
            categoryMealsAdapter.setMeals(meals)
        }
    }


    private fun showCategoryMeals(categoryName: String){
        mainViewMvvm.showCategoryMeals(categoryName)
        observeCategoryMealLiveData()
        binding.tvCategoryName.text = categoryName
    }


    companion object {
        private const val CATEGORY_NAME = "category_name"
        fun newInstance(categoryName: String): CategoryMealsDialogFragment {
            val fragment = CategoryMealsDialogFragment()
            val args = Bundle()
            args.putString(CATEGORY_NAME, categoryName)
            fragment.arguments = args
            return fragment
        }
    }
}
