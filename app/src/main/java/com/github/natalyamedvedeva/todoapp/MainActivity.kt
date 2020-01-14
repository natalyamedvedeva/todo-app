package com.github.natalyamedvedeva.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.github.natalyamedvedeva.todoapp.databinding.ActivityMainBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider

class MainActivity : AppCompatActivity() {
    //TODO repair toolbar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        EmojiManager.install(IosEmojiProvider())
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
        toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
        supportActionBar?.title = getString(R.string.main_activity_title)
    }
}
