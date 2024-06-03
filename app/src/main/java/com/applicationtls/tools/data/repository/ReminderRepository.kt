package com.applicationtls.tools.data.repository

import com.applicationtls.tools.data.local.database.reminder.ReminderDao
import com.applicationtls.tools.data.local.database.reminder.asDomain
import com.applicationtls.tools.data.local.database.reminder.asEntity
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun add(reminder: ReminderModel) {
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