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
    private lateinit var category: Category

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_edit_category_dialog,
            null,
            false
        )
        binding.category = arguments?.get("category") as Category

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Edit category")
        builder.setView(binding.root)
        builder.setPositiveButton(R.string.save){ _, _ ->
            val repository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
            repository.insert(binding.category as Category)
        }
            .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
        return builder.create()
    }
}
