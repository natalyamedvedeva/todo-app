package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category")
data class Category(
    @ColumnInfo(name = "cName")
    var name: String,
    @ColumnInfo(name = "color")
    var color: Int?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cId")
    var id: Long = 0L
}