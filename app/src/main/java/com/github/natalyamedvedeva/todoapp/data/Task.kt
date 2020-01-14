package com.github.natalyamedvedeva.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

enum class Priority {
    Low, Normal, High
}

@Entity(tableName = "task")
data class Task(
    var name: String,
    var priority: Priority,
    var date: Date
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var deadline: Date? = null
    var autoReschedule: Boolean = true
    var description: String? = null

    var images: List<String>? = null
}