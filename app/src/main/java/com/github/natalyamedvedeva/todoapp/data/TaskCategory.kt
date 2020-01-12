package com.github.natalyamedvedeva.todoapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["taskId", "categoryId"])
data class TaskCategoryCrossRef(
    val taskId: Long,
    val categoryId: Long
)

data class TaskWithCategories(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "categoryId",
        associateBy = Junction(TaskCategoryCrossRef::class)
    )
    val categories: List<Category>
)

data class CategoryWithTasks(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "taskId",
        associateBy = Junction(TaskCategoryCrossRef::class)
    )
    val tasks: List<Task>
)