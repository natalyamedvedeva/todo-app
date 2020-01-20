package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.facebook.drawee.view.SimpleDraweeView
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.databinding.FragmentImagesBinding
import com.stfalcon.frescoimageviewer.ImageViewer

class ImagesFragment : BaseFragment(), BaseFragment.OnImagesFragmentDataListener {

    private lateinit var binding: FragmentImagesBinding
    private var images: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_images, container, false)
        update()
        return binding.root
    }

    private fun update() {
        binding.imagesLayout.removeAllViews()
        images.forEachIndexed { index, path ->
            val view = SimpleDraweeView(context)
            val linearLayout = LinearLayout.LayoutParams(400, 400)
            if (index < images.lastIndex) {
                linearLayout.marginEnd = 32
            }
            view.layoutParams = linearLayout
            view.setImageURI(path)
            view.hierarchy.setPlaceholderImage(R.drawable.placeholder)
            binding.imagesLayout.addView(view)

            view.setOnClickListener {
                ImageViewer.Builder(context, images)
                    .setStartPosition(index)
                    .show()
            }
        }
    }

    override fun onImagesAppeared(images: List<String>) {
        this.images.clear()
        images.forEach { this.images.add("file://$it") }
        if (::binding.isInitialized) {
            update()
        }
    }
}