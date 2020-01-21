package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category
import com.vanniktech.emoji.EmojiTextView

class SelectableCategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(category: Category) {
        itemView.findViewById<EmojiTextView>(R.id.emoji_text_view).text =
            String.format("%s %s", category.emoji, category.name)
    }
}