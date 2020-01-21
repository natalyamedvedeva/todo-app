package com.github.natalyamedvedeva.todoapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.CategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentSelectCategoryDialogBinding
import com.github.natalyamedvedeva.todoapp.view.categoryList.CategoryListFragment
import com.github.natalyamedvedeva.todoapp.view.categoryList.SELECTABLE_TYPE
import java.lang.RuntimeException

class SelectCategoryDialog : DialogFragment() {

    private lateinit var binding: FragmentSelectCategoryDialogBinding
    private lateinit var categoryListFragment: BaseFragment.OnCategoryListFragmentDataListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_select_category_dialog,
            null,
            false
        )
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Select category")
        builder.setView(binding.root)
            .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
        return builder.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childFragment = CategoryListFragment(SELECTABLE_TYPE)
        childFragmentManager.beginTransaction()
            .replace(R.id.child_content_container, childFragment)
            .commit()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is BaseFragment.OnCategoryListFragmentDataListener) {
            categoryListFragment = childFragment
            updateChild()
        } else {
            throw RuntimeException("$childFragment must implements OnCategoryListFragmentDataListener")
        }
    }

    private fun updateChild() {
        val categoryRepository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
        val categoryListLiveData = categoryRepository.getCategoriesExcept(arguments?.get("ids") as List<Long>) // TODO: Fix it
        categoryListLiveData.observe(this, Observer {
            categoryListFragment.onCategoryListAppeared(it)
        })
    }
}
