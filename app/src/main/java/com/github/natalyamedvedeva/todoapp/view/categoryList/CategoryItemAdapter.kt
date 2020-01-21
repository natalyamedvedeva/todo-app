package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category
import java.lang.IllegalArgumentException

const val EDITABLE_TYPE = 0
const val SELECTABLE_TYPE = 1

class CategoryItemAdapter(private val type: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var categoryList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == EDITABLE_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_view, parent, false)
            return CategoryItemViewHolder(view)
        } else if (viewType == SELECTABLE_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.selectable_category_item_view, parent, false)
            return SelectableCategoryItemViewHolder(view)
        } else {
            throw IllegalArgumentException("Unknown view type")
        }
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

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == EDITABLE_TYPE) {
            (holder as CategoryItemViewHolder).bind(categoryList[position])
        } else if (getItemViewType(position) == SELECTABLE_TYPE) {
            (holder as SelectableCategoryItemViewHolder).bind(categoryList[position])
        }
    }
}