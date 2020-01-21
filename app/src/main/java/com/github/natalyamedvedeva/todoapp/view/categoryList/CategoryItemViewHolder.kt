package com.github.natalyamedvedeva.todoapp.view.categoryList

import android.view.View
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category
import com.vanniktech.emoji.EmojiTextView

class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(category: Category) {
        itemView.findViewById<EmojiTextView>(R.id.emoji_text_view).text =
            String.format("%s %s", category.emoji, category.name)
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