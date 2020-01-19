package com.github.natalyamedvedeva.todoapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * Saves the image to the internal storage and returns uuid.
 */
fun saveImage(context: Context, path: String): String {
    val img = BitmapFactory.decodeFile(path)
    val dir = ContextWrapper(context).getDir("images", Context.MODE_PRIVATE)
    val uuid = UUID.randomUUID().toString()
    val imgPath = File(dir, uuid)
    FileOutputStream(imgPath).use {
        img.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
    return uuid
}

fun getImagePath(context: Context, uuid: String): String {
    val dir = ContextWrapper(context).getDir("images", Context.MODE_PRIVATE)
    return File(dir, uuid).absolutePath
}