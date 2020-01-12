package com.github.natalyamedvedeva.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category")
data class Category(
    var name: String,
    var emoji: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}