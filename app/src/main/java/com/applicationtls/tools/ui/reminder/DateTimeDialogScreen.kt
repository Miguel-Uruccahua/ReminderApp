package com.applicationtls.tools.ui.reminder

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.applicationtls.tools.ui.reminder.domain.ReminderModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogDateTimePicker(
    onDismiss: () -> Unit,
    onConfirm: (ReminderModel) -> Unit,
    reminderModel: ReminderModel
) {
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.MAX) }
    val formattedDate by remember {
        derivedStateOf {
            ofPattern("dd-MM-yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            ofPattern("HH:mm")
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(2.dp, color = Color(0xFF204C5A), shape = RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        dateDialogState.show()
                    }) {
                        Text(text = "Selecciona Fecha")
                    }
                    Text(text = formattedDate)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        timeDialogState.show()
                    }) {
                        Text(text = "Selecciona Hora")
                    }
                    Text(text = formattedTime)
                }
                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton(text = "Ok") {

                        }
                        negativeButton(text = "Cancel")
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date",
                    ) {
                        pickedDate = it
                    }
                }
                MaterialDialog(
                    dialogState = timeDialogState,
                    buttons = {
                        positiveButton(text = "Ok") {

                        }
                        negativeButton(text = "Cancel")
                    }
                ) {
                    timepicker(
                        initialTime = LocalTime.MAX,
                        title = "Pick a time",
                        is24HourClock = true,
                    ) {
                        Log.e("SELECTED", it.toString())
                        pickedTime = it
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonColors(
                            contentColor = Color(0xFF163C5D),
                            containerColor = Color(0xFF163C5D),
                            disabledContentColor = Color(0xFF163C5D),
                            disabledContainerColor = Color(0xFF163C5D),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            reminderModel.apply { time = "$formattedDate $formattedTime" }
                            onConfirm(reminderModel)
                        },
                        colors = ButtonColors(
                            contentColor = Color(0xFF163C5D),
                            containerColor = Color(0xFF163C5D),
                            disabledContentColor = Color(0xFF163C5D),
                            disabledContainerColor = Color(0xFF163C5D),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Guardar",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogDateTimePicker3(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    dialogState: MaterialDialogState
) {
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.MAX) }
    val formattedDate by remember {
        derivedStateOf {
            ofPattern("dd-MM-yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            ofPattern("HH:mm")
                .format(pickedTime)
        }
    }
    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(text = "Ok") {
                timeDialogState.show()
            }
            negativeButton(text = "Cancel"){
                dialogState.hide()
                onDismiss()
            }
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                onConfirm("$formattedDate $formattedTime")
            }
            negativeButton(text = "Cancel"){
                timeDialogState.hide()
                onDismiss()
            }
        }
    ) {
        timepicker(
            initialTime = LocalTime.MAX,
            title = "Pick a time",
            is24HourClock = true,
        ) {
            pickedTime = it
            Log.e("SELECTED", pickedTime.toString())
        }
    }
}



