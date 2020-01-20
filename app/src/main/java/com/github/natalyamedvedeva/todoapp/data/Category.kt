package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category", indices = [ Index("name", unique = true),
                                            Index("emoji", unique = true) ])
data class Category(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "emoji")
    var emoji: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (other is Category && name == other.name && emoji == other.emoji) return true
        return false
    }
}