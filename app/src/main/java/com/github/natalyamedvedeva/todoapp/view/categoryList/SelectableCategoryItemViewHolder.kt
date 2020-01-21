package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category

class SelectableCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(category: Category) {
        category.color?.let {
            itemView.findViewById<CardView>(R.id.color).setCardBackgroundColor(it)
        }
        itemView.findViewById<TextView>(R.id.name_text_view).text = category.name
        itemView.setOnClickListener {
        }
    }
}