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
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanpra.composematerialdialogs.rememberMaterialDialogState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderScreen(
    navController: NavHostController, viewModel: ReminderViewModel = hiltViewModel()
) {
    val content by viewModel.data.observeAsState(initial = "")
    val dateTime by viewModel.dateTime.observeAsState(initial = "No Definido")

    val uiState by viewModel.listState.collectAsStateWithLifecycle()
    var showDateTime by remember { mutableStateOf(false) }
    val dateDialogState = rememberMaterialDialogState()
    val focusRequester = remember { FocusRequester() }

    if (showDateTime) {
        DialogDateTimePicker3(onDismiss = { showDateTime = false }, onConfirm = {
            viewModel.onDateTimeChange(it)
        }, dialogState = dateDialogState)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFDFDBDB)), Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Nuevo Recordatorio: ",
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = content,
            onValueChange = { viewModel.onDataChange(it) },
            label = { Text(text = "Contenido") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 10.dp).focusRequester(focusRequester),
            )
        Text(text = "Fecha y Hora: $dateTime")
        Spacer(modifier = Modifier.size(8.dp))
        Row() {
            Button(onClick = { viewModel.addReminder() }) {
                Text(text = "Guardar")
                Spacer(modifier = Modifier.size(2.dp))
                Icon(imageVector = Icons.Outlined.Done, contentDescription = null)
            }
            Spacer(modifier = Modifier.size(4.dp))
            Button(onClick = {
                dateDialogState.show()
                showDateTime = true
            }) {
                Text(text = "Fecha y Hora")
                Spacer(modifier = Modifier.size(2.dp))
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
            }
        }

        when (uiState) {
            is ReminderUiState.Error -> {}
            is ReminderUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ReminderUiState.Success -> {
                val content = (uiState as ReminderUiState.Success).data
                if (!content.isNullOrEmpty()) {
                    AccessList(content, viewModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccessList(itemsAccess: List<ReminderModel>, viewModel: ReminderViewModel) {
    Text(
        text = "Recordatorios: ",
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp),
        fontWeight = FontWeight.Bold
    )
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(itemsAccess, key = { it.id }) { access ->
            AccessItem(access, viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccessItem(item: ReminderModel, viewModel: ReminderViewModel) {

    var isExpanded by remember { mutableStateOf(false) }
    val icon = if (!isExpanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp
    var showDialogDelete by remember { mutableStateOf(false) }
    var showDialogUpdate by remember { mutableStateOf(false) }
    var showDialogDateTime by remember { mutableStateOf(false) }

    if (showDialogDelete) {
        CustomDialogDelete(onConfirm = {
            viewModel.deleteReminder(item)
            showDialogDelete = false
        }, onDismiss = { showDialogDelete = false })
    }
    if (showDialogUpdate) {
        CustomDialogUpdate(onConfirm = {
            viewModel.updateReminder(it)
            showDialogUpdate = false
        }, onDismiss = { showDialogUpdate = false }, reminderModel = item
        )
    }
    if (showDialogDateTime) {
        DialogDateTimePicker(onConfirm = {
            viewModel.updateReminder(it)
            showDialogDateTime = false
        }, onDismiss = { showDialogDateTime = false }, reminderModel = item
        )
    }
    Box(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.small)
            .background(Color(0xFFD9F6FF))
            .border(
                width = 4.dp, color = Color(0xFF290569)
            )
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    Modifier.clickable { isExpanded = !isExpanded })
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(text = item.content)
                    Text(text = "Fecha y Hora: " + item.time)
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            AnimatedVisibility(
                visible = isExpanded, enter = fadeIn(
                    initialAlpha = 0.4f
                ), exit = fadeOut(
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                Row() {
                    Button(onClick = { showDialogUpdate = true }) {
                        Text(text = "Editar")
                        Spacer(modifier = Modifier.size(2.dp))
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                    Button(onClick = { showDialogDelete = true }) {
                        Text(text = "Eliminar")
                        Spacer(modifier = Modifier.size(2.dp))
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(5.dp))
                    Button(onClick = { showDialogDateTime = true }) {
                        Text(text = "Programar")
                        Spacer(modifier = Modifier.size(2.dp))
                        Icon(
                            imageVector = Icons.Filled.DateRange, contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

