package com.github.natalyamedvedeva.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategories(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM category WHERE cId NOT IN (:ids)")
    fun getCategoriesExcept(ids: List<Long>) :LiveData<List<Category>>
}