package com.applicationtls.tools.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.applicationtls.tools.R
import com.applicationtls.tools.data.local.database.reminder.ReminderDao
import com.applicationtls.tools.data.local.database.reminder.asDomain
import com.applicationtls.tools.data.local.database.reminder.asEntity
import com.applicationtls.tools.utils.CHANEL_ID
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    suspend fun startNotification()
}

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    @ApplicationContext val context: Context
) : ReminderRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun add(reminder: ReminderModel) {
        if (reminder.time == "No definido"){
            reminder.apply { time = getNow() }
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

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun startNotification(){
        var data = reminderDao.selectDataByDate(getNow())
        Log.e("here",data.toString()+getNow())
        for (row in data){
            createNotification(row.content)
            reminderDao.updateRemind(row.apply { isDone=true })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNow(): String {
        val now = Instant.now()
        val localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        return localDateTime.format(formatter)
    }

    @SuppressLint("MissingPermission")
    private fun createNotification(content:String){
        var builder = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("ReminderApp Notify:")
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

}