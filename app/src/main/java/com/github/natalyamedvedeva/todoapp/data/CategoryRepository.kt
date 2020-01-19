package com.github.natalyamedvedeva.todoapp.data

class CategoryRepository private constructor(private val categoryDao: CategoryDao) {

    fun getCategories() = categoryDao.getCategories()

    fun insert(category: Category) {
        Thread {
            categoryDao.insert(category)
        }.start()
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: CategoryRepository? = null

        fun getInstance(categoryDao: CategoryDao) =
            instance ?: synchronized(this) {
                instance
                    ?: CategoryRepository(categoryDao).also { instance = it }
            }
    }
}