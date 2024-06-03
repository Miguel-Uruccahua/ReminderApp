package com.applicationtls.tools.ui.reminder.domain

data class ReminderModel(
    val id :Int = 0,
    var content: String = "",
    val time: String = "",
    val isDone: Boolean = false
){

}
