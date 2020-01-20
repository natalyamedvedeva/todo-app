package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

    fun addItem(category: Category) {
        categoryList.add(category)
        notifyItemInserted(categoryList.size - 1)
    }

    fun removeItem(position: Int) {
        categoryList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun resetItems(categories: List<Category>) {
        val diffCallback: DiffUtil.Callback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = this@CategoryItemAdapter.categoryList.size

            override fun getNewListSize(): Int = categories.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                this@CategoryItemAdapter.categoryList[oldItemPosition].id == categories[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                this@CategoryItemAdapter.categoryList[oldItemPosition] == categories[newItemPosition]
        }
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.categoryList.clear()
        this.categoryList.addAll(categories)
        diffResult.dispatchUpdatesTo(this)
    }
}