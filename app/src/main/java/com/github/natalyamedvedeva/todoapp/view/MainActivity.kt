package com.github.natalyamedvedeva.todoapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.natalyamedvedeva.todoapp.R
import com.github.natalyamedvedeva.todoapp.databinding.ActivityMainBinding
import com.github.natalyamedvedeva.todoapp.utils.rescheduleTasks
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onStart() {
        super.onStart()
        updateTasks()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun updateTasks() {
        val today = Calendar.getInstance()
        today[Calendar.HOUR_OF_DAY] = 0
        today[Calendar.MINUTE] = 0
        today[Calendar.SECOND] = 0

        val context = this
        val timer = Timer()
        rescheduleTasks(context, context)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    rescheduleTasks(context, context)
                }
            }
        }, today.time, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))
    }
}
