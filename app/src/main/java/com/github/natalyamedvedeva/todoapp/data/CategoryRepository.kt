package com.github.natalyamedvedeva.todoapp.data

class CategoryRepository private constructor(private val categoryDao: CategoryDao) {

    fun getCategories() = categoryDao.getCategories()

    fun insert(category: Category) {
        Thread {
            if (category.id == 0L) {
                categoryDao.insert(category)
            } else {
                categoryDao.update(category)
            }
        }.start()
    }

    fun delete(category: Category) {
        Thread {
            categoryDao.delete(category)
        }.start()
    }

    fun getCategoriesExcept(ids: List<Long>) = categoryDao.getCategoriesExcept(ids)

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