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

package com.applicationtls.tools.ui.reminder

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicationtls.tools.data.repository.ReminderRepository
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date

import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _data = MutableLiveData<String>()
    var data: LiveData<String> = _data

    fun onDataChange(id: String) {
        _data.value = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addReminder(){
        viewModelScope.launch {
            runCatching {
                reminderRepository.add(ReminderModel(
                    content = _data.value ?: "Agregado sin datos",
                    time = LocalDateTime.now().toString(),
                    isDone = true
                ))
            }.onSuccess {
                _data.value = ""
            }.onFailure {
                Log.e("ReminderVM","Fallo al insertar en: $it")
            }
        }
    }

    val listState:StateFlow<ReminderUiState> = reminderRepository.getData().map { ReminderUiState.Success(it) }
        .catch { ReminderUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),ReminderUiState.Loading )


    fun deleteReminder(reminderModel: ReminderModel){
        viewModelScope.launch {
            runCatching {
                reminderRepository.delete(reminderModel)
            }.onFailure {
                Log.e("ReminderVM","Fallo al Eliminar en: $it")
            }
        }
    }

    fun updateReminder(reminderModel: ReminderModel){
        viewModelScope.launch {
            runCatching {
                reminderRepository.update(reminderModel)
            }.onFailure {
                Log.e("ReminderVM","Fallo al Actualizar en: $it")
            }
        }
    }

}

sealed interface ReminderUiState {
    object Loading : ReminderUiState
    data class Error(val throwable: Throwable) : ReminderUiState
    data class Success(val data: List<ReminderModel>) : ReminderUiState
}
