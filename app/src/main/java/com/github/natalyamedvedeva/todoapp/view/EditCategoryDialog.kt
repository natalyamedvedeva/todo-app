package com.github.natalyamedvedeva.todoapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.CategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentEditCategoryDialogBinding

class EditCategoryDialog : DialogFragment() {

    private lateinit var binding: FragmentEditCategoryDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_edit_category_dialog,
            null,
            false
        )

        val category = arguments?.get("category") as Category
        binding.category = category.copy()

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Edit category")
        builder.setView(binding.root)
        builder.setPositiveButton(R.string.save){ _, _ ->
            val repository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
            val changedCategory = binding.category as Category
            changedCategory.id = category.id
            repository.insert(changedCategory)
        }
            .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
        return builder.create()
    }


}
