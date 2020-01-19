package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category

class CategoryItemAdapter: RecyclerView.Adapter<CategoryItemViewHolder>() {

    private var categoryList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_view, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size

    fun addItems(categories: Collection<Category>) {
        categoryList.addAll(categories)
        notifyDataSetChanged()
    }

    fun clearItems() {
        categoryList.clear()
        notifyDataSetChanged()
    }
}