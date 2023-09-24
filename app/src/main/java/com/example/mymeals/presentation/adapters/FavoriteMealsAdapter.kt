package com.example.mymeals.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymeals.data.model.Meal
import com.example.mymeals.data.model.MealList
import com.example.mymeals.databinding.RandomItemBinding

class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsViewHolder>(){
    private  var mealsList: MealList = MealList(emptyList())
    lateinit var onItemClick:((Meal) -> Unit)

    fun setMeals(mealsList: MealList){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
    inner class FavoriteMealsViewHolder(val binding: RandomItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList.meals[position].strMealThumb)
            .into(holder.binding.imgItem)
        Log.d("Adapter", mealsList.meals[position].strMeal)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList.meals[position])
        }
        holder.binding.tvView.text = mealsList.meals[position].strMeal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder {
        return FavoriteMealsViewHolder(RandomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return mealsList.meals.size
    }

}