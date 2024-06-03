package com.applicationtls.tools.data.local.database.tools

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToolsDao {
    @Query("SELECT * FROM toolsentity ORDER BY uid DESC LIMIT 10")
    fun getToolss(): Flow<List<ToolsEntity>>

    @Insert
    suspend fun insertTools(item: ToolsEntity)
}
