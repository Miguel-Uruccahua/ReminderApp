/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.applicationtls.tools.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.applicationtls.tools.data.local.database.tools.ToolsEntity
import com.applicationtls.tools.data.local.database.tools.ToolsDao
import javax.inject.Inject

interface ToolsRepository {
    val toolss: Flow<List<String>>
    suspend fun add(name: String)
}

class DefaultToolsRepository @Inject constructor(
    private val toolsDao: ToolsDao
) : ToolsRepository {

    override val toolss: Flow<List<String>> =
        toolsDao.getToolss().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        toolsDao.insertTools(ToolsEntity(name = name))
    }
}
