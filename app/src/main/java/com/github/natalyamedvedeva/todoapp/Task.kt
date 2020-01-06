package com.github.natalyamedvedeva.todoapp

import java.util.*
import java.io.Serializable

enum class Priority {
    Low, Normal, High
}

class Task(var name: String, var priority: Priority) : Serializable {

    var deadline: Calendar? = null
    var autoTransfer: Boolean = true
    var description: String? = null

}