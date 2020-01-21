package com.github.natalyamedvedeva.todoapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.AppDatabase
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.CategoryRepository
import com.github.natalyamedvedeva.todoapp.databinding.FragmentEditCategoryDialogBinding
import com.pes.androidmaterialcolorpickerdialog.ColorPicker

class EditCategoryDialog : DialogFragment() {

    private lateinit var binding: FragmentEditCategoryDialogBinding

    private val defaultColor = Color.rgb(126, 200, 80)
    private var color = defaultColor

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.fragment_edit_category_dialog,
            null,
            false
        )

        val category = arguments?.get("category") as Category

        val colorPicker = ColorPicker(activity, Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor))
        category.color?.let {
            color = it
        }

        colorPicker.setCallback {
            binding.colorButton.setBackgroundColor(it)
            color = it
            colorPicker.cancel()
        }

        binding.colorButton.setBackgroundColor(color)
        binding.colorButton.setOnClickListener {
            colorPicker.color = color
            colorPicker.show()
        }

        binding.nameEditText.setText(category.name)

        return AlertDialog.Builder(activity)
            .setTitle("Edit category")
            .setView(binding.root)
            .setPositiveButton(R.string.save){ _, _ ->
                val repository = CategoryRepository.getInstance(AppDatabase.getInstance(requireContext()).categoryDao())
                val changedCategory = Category(binding.nameEditText.text.toString(), color)
                changedCategory.id = category.id
                repository.insert(changedCategory)
            }.setNegativeButton(R.string.cancel) { _, _ -> dismiss() }
            .create()
    }
}
