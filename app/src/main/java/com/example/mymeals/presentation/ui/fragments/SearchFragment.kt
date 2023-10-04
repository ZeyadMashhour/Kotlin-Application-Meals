package com.example.mymeals.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymeals.R
import com.example.mymeals.databinding.FragmentFavoriteBinding
import com.example.mymeals.databinding.FragmentSearchBinding
import com.example.mymeals.presentation.adapters.CategoriesAdapter
import com.example.mymeals.presentation.ui.viewmodels.MainViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var mainMvvm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainMvvm = ViewModelProvider(this)[MainViewModel::class.java]
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareCategoriesRecyclerView()
        mainMvvm.getCategories()

        // Call observeCategoriesLiveData here
        observeCategoriesLiveData()
    }

    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        // Make sure that observeCategoriesLiveData() returns LiveData in your MainViewModel
        mainMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            // Update your UI with the observed categories here using categoriesAdapter
            categoriesAdapter.setCategoryList(categories)
        }
    }
}
