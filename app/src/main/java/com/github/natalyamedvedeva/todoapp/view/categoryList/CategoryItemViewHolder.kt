package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(category: Category) {
        category.color?.let {
            itemView.findViewById<CardView>(R.id.color).setCardBackgroundColor(it)
        }
        itemView.findViewById<TextView>(R.id.name_text_view).text = category.name
        itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.editCategoryDialog,
                bundleOf("category" to category)
            )
        )
        itemView.findViewById<ImageButton>(R.id.delete_category_button).setOnClickListener {
            //TODO delete
        }
    }
}