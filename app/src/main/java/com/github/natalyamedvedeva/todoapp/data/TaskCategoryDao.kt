package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface TaskCategoryDao {

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksWithCategories(date: Date): LiveData<List<TaskWithCategories>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTaskCategory(taskCategory: TaskCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task): Long

    @Query("DELETE FROM taskcategorycrossref WHERE taskId = :id")
    fun deleteByTask(id: Long)

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task WHERE tName LIKE '%' || :name || '%'")
    fun getTasksWithCategoriesByName(name: String): LiveData<List<TaskWithCategories>>
}