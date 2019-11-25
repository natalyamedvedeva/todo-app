package com.github.natalyamedvedeva.todoapp

import java.util.*
import java.io.Serializable

enum class Priority {
    Low, Normal, High, Urgent, Immediate
}

class Task(var name: String, var priority: Priority) : Serializable {

    var description: String? = null
    var deadline: Date? = null
    var autoTransfer: Boolean = true

}