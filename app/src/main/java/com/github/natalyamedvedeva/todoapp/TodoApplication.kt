package com.github.natalyamedvedeva.todoapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);
    }
}