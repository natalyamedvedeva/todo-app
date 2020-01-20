package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.data.Category
import com.github.natalyamedvedeva.todoapp.databinding.FragmentCategoriesBinding
import kotlinx.android.synthetic.main.category_card_view.view.*

class CategoriesFragment : BaseFragment(), BaseFragment.OnCategoriesFragmentDataListener {

    private lateinit var binding: FragmentCategoriesBinding
    private var categories: MutableList<Category> = mutableListOf()

    var editable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_categories, container, false)
        update()
        return binding.root
    }

    private fun update() {
        binding.categoriesLayout.removeAllViews()
        categories.forEach {
            val view = layoutInflater.inflate(R.layout.category_card_view,  binding.categoriesLayout, false)
            val text = it.emoji + " " + it.name
            view.emoji_edit_text.text = text
            binding.categoriesLayout.addView(view)
            if (editable) {
                view.setOnClickListener { v ->
                    val index = binding.categoriesLayout.indexOfChild(v)
                    categories.removeAt(index)
                    binding.categoriesLayout.removeView(v)
                    update()
                }
            }
        }
        if (editable) {
            val button = Button(context)
            button.text = if (categories.isEmpty()) getString(R.string.add_category) else getString(R.string.add)
            binding.categoriesLayout.addView(button)
            button.setOnClickListener {
                categories.add(Category("Caaaaaat", "\uD83D\uDC08"))
                update()
            }
        }
    }

    override fun onCategoriesAppeared(categories: List<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)
        if (::binding.isInitialized) {
            update()
        }
    }
}