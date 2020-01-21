package com.github.natalyamedvedeva.todoapp.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.CategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentSelectCategoryDialogBinding
import com.github.natalyamedvedeva.todoapp.view.categoryList.CategoryItemAdapter
import com.github.natalyamedvedeva.todoapp.view.categoryList.SELECTABLE_TYPE

class SelectCategoryDialog(private val ids: List<Long>) : DialogFragment(),
    BaseFragment.OnCategorySelectedDataListener {

    private lateinit var binding: FragmentSelectCategoryDialogBinding
    private lateinit var adapter: CategoryItemAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_select_category_dialog,
            null,
            false
        )
        initRecyclerView()
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Select category")
        builder.setView(binding.root)
            .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
            .setPositiveButton(R.string.add) { _, _ ->
                findNavController()
                    .navigate(R.id.editCategoryDialog, bundleOf("category" to Category("", null)))
            }
        return builder.create()
    }

    private fun initRecyclerView() {
        val categoriesRecyclerView = binding.recyclerView
        categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        adapter = CategoryItemAdapter(SELECTABLE_TYPE)
            .also { it.setListener(this) }
        categoriesRecyclerView.adapter = adapter
        update()
    }

    private fun update() {
        val categoryRepository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
        val categoryListLiveData = categoryRepository.getCategoriesExcept( ids)
        categoryListLiveData.observe(this, Observer {
            if(it.isEmpty()) {
                binding.msg.visibility = View.VISIBLE
            }
            adapter.resetItems(it)
        })
    }

    override fun onCategorySelected(category: Category) {
        val intent = Intent()
        intent.putExtra("category", category)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        dismiss()
    }
}
