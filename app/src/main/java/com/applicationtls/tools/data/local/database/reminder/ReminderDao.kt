package com.applicationtls.tools.data.local.database.reminder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert
    suspend fun insertRemind(item: ReminderEntity)

    @Update
    suspend fun updateRemind(item: ReminderEntity)

    @Delete
    suspend fun deleteRemind(item: ReminderEntity)

    @Query("SELECT * FROM reminderentity ORDER BY id desc ")
    fun getData(): Flow<List<ReminderEntity>>

}