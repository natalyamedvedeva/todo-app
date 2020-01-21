package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
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
        retainInstance = true
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_categories, container, false)
        update()
        return binding.root
    }

    private fun update() {
        binding.categoriesLayout.removeAllViews()
        categories.forEach {
            val view = layoutInflater.inflate(R.layout.category_card_view,  binding.categoriesLayout, false)
            it.color?.let { color -> view.setBackgroundColor(color) }
            val text = it.name
            view.textView.text = text
            binding.categoriesLayout.addView(view)
            if (editable) {
                view.findViewById<ImageView>(R.id.delete_button).setOnClickListener {
                    val index = binding.categoriesLayout.indexOfChild(view)
                    categories.removeAt(index)
                    update()
                }
            }
        }
        if (editable) {
            val button = Button(context)
            button.text = if (categories.isEmpty()) getString(R.string.add_category) else getString(R.string.add)
            binding.categoriesLayout.addView(button)
            button.setOnClickListener {
                findNavController()
                    .navigate(R.id.selectCategoryDialog, bundleOf("ids" to categories.map { it.id }))
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

    fun addCategory(category: Category) {
        categories.add(category)
    }
}