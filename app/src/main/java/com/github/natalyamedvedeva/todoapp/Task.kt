package com.github.natalyamedvedeva.todoapp

import java.util.*

enum class Priority {
    Low, Normal, High, Urgent, Immediate
}

class Task(var name: String, var priority: Priority) {

    var description: String? = null
    var deadline: Date? = null
    var autoTransfer: Boolean = true

}