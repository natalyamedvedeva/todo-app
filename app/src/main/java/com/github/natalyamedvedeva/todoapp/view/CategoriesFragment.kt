package com.github.natalyamedvedeva.todoapp.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.CategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentCategoriesBinding
import com.github.natalyamedvedeva.todoapp.view.categoryList.CategoryListFragment
import java.lang.RuntimeException

class CategoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var child: OnCategoryListFragmentDataListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_categories, container, false
        )
        binding.addCategoryButton.setOnClickListener {
            findNavController().navigate(R.id.editCategoryDialog, bundleOf("category" to Category("", "")))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childFragment = CategoryListFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.child_fragment_container, childFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is OnCategoryListFragmentDataListener) {
            child = childFragment
            updateChild()
        } else {
            throw RuntimeException("$childFragment must implements OnCategoryListFragmentDataListener")
        }
    }

    private fun updateChild() {
        val categoryRepository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
        child.onCategoryListAppeared(categoryRepository.getCategories())
    }
}
