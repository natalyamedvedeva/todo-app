package com.github.natalyamedvedeva.todoapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTaskList(date: Date) : List<Task>
}