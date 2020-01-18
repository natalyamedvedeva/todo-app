package com.github.natalyamedvedeva.todoapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_category_dialog, container, false)
        category = arguments?.get("category") as Category
        binding.category = category
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Edit category")
        builder.setView(R.layout.fragment_edit_category_dialog)
        builder.setPositiveButton(R.string.save){ _, _ ->
            val repository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
            repository.insert(category)
        }
            .setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
        return builder.create()
    }
}
