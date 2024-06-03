package com.applicationtls.tools.data.local.database.reminder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0,
    val content: String = "",
    val time: String = "",
    val isDone: Boolean = false
)