package com.github.natalyamedvedeva.todoapp.data

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(primaryKeys = ["taskId", "categoryId"], indices = [Index("taskId"), Index("categoryId")])
data class TaskCategoryCrossRef(
    @ColumnInfo(name = "taskId")
    val taskId: Long,
    @ColumnInfo(name = "categoryId")
    val categoryId: Long
)

data class TaskWithCategories (
    @Embedded
    var task: Task,
    @Relation(
        parentColumn = "tId",
        entity = Category::class,
        entityColumn = "cId",
        associateBy = Junction(
            value = TaskCategoryCrossRef::class,
            parentColumn = "taskId",
            entityColumn = "categoryId"
        )
    )
    val categories: MutableList<Category>
) : Serializable {
    var name: String
        get() = task.name
        set(value) { task.name = value }

    var priority: Priority
        get() = task.priority
        set(value) { task.priority = value }

    var deadline: Date?
        get() = task.deadline
        set(value) { task.deadline = value }

    var date: Date
        get() = task.date
        set(value) { task.date = value }

    val id: Long
        get() = task.id

    var autoReschedule: Boolean
        get() = task.autoReschedule
        set(value) { task.autoReschedule = value }

    var description: String?
        get() = task.description
        set(value) { task.description = value }

    var images: MutableList<String>
        get() = task.images
        set(value) { task.images.clear(); task.images.addAll(value) }

    var done: Boolean
        get() = task.done
        set(value) { task.done = value }
}