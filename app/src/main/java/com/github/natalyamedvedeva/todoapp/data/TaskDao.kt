package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTaskList(date: Date) : LiveData<List<Task>>
}