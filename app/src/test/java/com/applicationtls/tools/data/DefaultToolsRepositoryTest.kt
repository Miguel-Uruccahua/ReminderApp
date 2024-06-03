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

package com.applicationtls.tools.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.applicationtls.tools.data.local.database.tools.ToolsEntity
import com.applicationtls.tools.data.local.database.tools.ToolsDao
import com.applicationtls.tools.data.repository.DefaultToolsRepository

/**
 * Unit tests for [DefaultToolsRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultToolsRepositoryTest {

    @Test
    fun toolss_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultToolsRepository(FakeToolsDao())

        repository.add("Repository")

        assertEquals(repository.toolss.first().size, 1)
    }

}

private class FakeToolsDao : ToolsDao {

    private val data = mutableListOf<ToolsEntity>()

    override fun getToolss(): Flow<List<ToolsEntity>> = flow {
        emit(data)
    }

    override suspend fun insertTools(item: ToolsEntity) {
        data.add(0, item)
    }
}
