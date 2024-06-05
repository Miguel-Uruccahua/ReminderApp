package com.applicationtls.tools.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.applicationtls.tools.data.local.database.reminder.ReminderDao
import com.applicationtls.tools.data.local.database.reminder.asDomain
import com.applicationtls.tools.data.local.database.reminder.asEntity
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ReminderRepository {
    suspend fun add(reminder:ReminderModel)
    suspend fun delete(reminder: ReminderModel)

    suspend fun update(reminder: ReminderModel)
    fun getData():Flow<List<ReminderModel>>
}

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
) : ReminderRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun add(reminder: ReminderModel) {
        if (reminder.time == "No definido"){
            val now = Instant.now()
            val localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val formattedDateTime = localDateTime.format(formatter)
            reminder.apply { time = formattedDateTime }
        }
        reminderDao.insertRemind(reminder.asEntity())
    }

    override suspend fun delete(reminder: ReminderModel) {
        reminderDao.deleteRemind(reminder.asEntity())
    }

    override suspend fun update(reminder: ReminderModel) {
        reminderDao.updateRemind(reminder.asEntity())
    }

    override fun getData(): Flow<List<ReminderModel>> {
        return reminderDao.getData().map { items->items.map { it.asDomain() } }
    }


}