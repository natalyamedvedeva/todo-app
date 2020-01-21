package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
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

    override fun equals(other: Any?): Boolean {
        if (other is Category && name == other.name && color == other.color) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}