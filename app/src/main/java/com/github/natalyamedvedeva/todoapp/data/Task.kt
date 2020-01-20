package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

enum class Priority {
    Low, Normal, High
}

@Entity(tableName = "task")
class Task(
    @ColumnInfo(name = "tName")
    var name: String,
    var priority: Priority,
    var date: Date
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tId")
    var id: Long = 0L

    var deadline: Date? = null
    var autoReschedule: Boolean = true
    var description: String? = null

    var images: MutableList<String> = mutableListOf()
}