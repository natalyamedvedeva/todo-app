package com.github.natalyamedvedeva.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.natalyamedvedeva.todoapp.R
import java.io.Serializable
import java.util.*

enum class Priority(val color: Int) {
    Low(R.color.lowPriority),
    Normal(R.color.normalPriority),
    High(R.color.highPriority)
}

@Entity(tableName = "task")
data class Task(
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

    fun isDeadlineClose(): Boolean {
        val nextDay = Calendar.getInstance()
        nextDay.add(Calendar.DAY_OF_MONTH, 1)

        if (deadline != null && nextDay.time >= deadline) {
            return true
        }
        return false
    }
}