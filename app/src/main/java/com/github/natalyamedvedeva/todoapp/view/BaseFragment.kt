package com.github.natalyamedvedeva.todoapp.view

import androidx.fragment.app.Fragment
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories

open class BaseFragment: Fragment() {
    interface OnTaskListFragmentDataListener {
        fun onTaskListAppeared(data: List<TaskWithCategories>)
    }

    interface OnCategoryListFragmentDataListener {
        fun onCategoryListAppeared(data: List<Category>)
    }

    interface OnImagesFragmentDataListener {
        fun onImagesAppeared(images: List<String>)
    }

    interface OnCategoriesFragmentDataListener {
        fun onCategoriesAppeared(categories: List<Category>)
    }
}