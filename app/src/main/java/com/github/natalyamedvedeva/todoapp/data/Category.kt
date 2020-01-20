package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category", indices = [ Index("cName", unique = true),
                                            Index("emoji", unique = true) ])
class Category(
    @ColumnInfo(name = "cName")
    var name: String,
    @ColumnInfo(name = "emoji")
    var emoji: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cId")
    var id: Long = 0L

    override fun equals(other: Any?): Boolean {
        if (other is Category && name == other.name && emoji == other.emoji) return true
        return false
    }
}