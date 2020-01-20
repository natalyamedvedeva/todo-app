package com.github.natalyamedvedeva.todoapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);
        EmojiManager.install(IosEmojiProvider())
    }
}