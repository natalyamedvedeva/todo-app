package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Transaction
import java.util.*

@Dao
interface TaskCategoryDao {

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksWithCategories(date: Date): LiveData<List<TaskWithCategories>>
}