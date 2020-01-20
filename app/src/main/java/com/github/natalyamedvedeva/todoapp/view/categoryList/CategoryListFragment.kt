package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.databinding.FragmentCategoryListBinding
import com.github.natalyamedvedeva.todoapp.view.BaseFragment

class CategoryListFragment : BaseFragment(),
    BaseFragment.OnCategoryListFragmentDataListener {

    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var categoryItemAdapter: CategoryItemAdapter
    private var categoryListLiveData: LiveData<List<Category>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_category_list, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        categoriesRecyclerView = binding.categoriesRecyclerView
        categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        categoryItemAdapter = CategoryItemAdapter()
        categoriesRecyclerView.adapter = categoryItemAdapter
        updateCategoryList()
    }

    private fun updateCategoryList() {
        categoryListLiveData?.observe(this, Observer {
            categoryItemAdapter.resetItems(it)
        })
    }

    override fun onCategoryListAppeared(data: LiveData<List<Category>>) {
        categoryListLiveData = data
        updateCategoryList()
    }
}
