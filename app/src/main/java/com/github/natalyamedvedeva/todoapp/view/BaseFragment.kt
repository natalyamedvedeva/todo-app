package com.github.natalyamedvedeva.todoapp.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.data.Task
import com.github.natalyamedvedeva.todoapp.data.TaskWithCategories

open class BaseFragment: Fragment() {
    interface OnTaskListFragmentDataListener {
        fun onTaskListAppeared(data: LiveData<List<TaskWithCategories>>)
    }

    interface OnCategoryListFragmentDataListener {
        fun onCategoryListAppeared(data: LiveData<List<Category>>)
    }

    interface OnImagesFragmentDataListener {
        fun onImagesAppeared(images: List<String>)
    }

    interface OnCategoriesFragmentDataListener {
        fun onCategoriesAppeared(categories: List<Category>)
    }
}