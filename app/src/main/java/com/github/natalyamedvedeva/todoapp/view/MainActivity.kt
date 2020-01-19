package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.facebook.drawee.backends.pipeline.Fresco
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.databinding.ActivityMainBinding
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fresco.initialize(this);
        EmojiManager.install(IosEmojiProvider())

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}