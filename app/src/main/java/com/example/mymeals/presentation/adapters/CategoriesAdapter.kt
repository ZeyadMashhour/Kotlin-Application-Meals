package com.example.mymeals.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymeals.data.model.Category
import com.example.mymeals.data.model.CategoryList
import com.example.mymeals.data.model.Meal
import com.example.mymeals.databinding.CategoryItemBinding

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    lateinit var onItemClick:((Category) -> Unit)
    private var categoriesList: CategoryList = CategoryList(emptyList())

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoriesList.categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList.categories[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList.categories[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoriesList.categories[position])
        }
    }

    fun setCategoryList(categories: CategoryList) {
        this.categoriesList = categories
        notifyDataSetChanged()
    }
}