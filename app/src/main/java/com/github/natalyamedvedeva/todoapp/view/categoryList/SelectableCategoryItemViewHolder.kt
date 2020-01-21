package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category

class SelectableCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(category: Category) {
        itemView.findViewById<TextView>(R.id.text_view).text =
            String.format("%s %s", category.color, category.name)
    }
}