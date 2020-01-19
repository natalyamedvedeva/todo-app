package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTaskList(date: Date) : LiveData<List<Task>>
}