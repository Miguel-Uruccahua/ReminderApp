package com.applicationtls.tools.ui.reminder

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun CustomDialogDelete(
    onDismiss:()->Unit,
    onConfirm:()->Unit,
) {
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
                .border(2.dp, color = Color(0xFF163C5D), shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "¿Eliminar Recordatorio?",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
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
                            .weight(1f)
                        ,
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
                            onConfirm()
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
                            text = "Confirmar",
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

@Composable
fun CustomDialogUpdate(
    onDismiss:()->Unit,
    onConfirm:(ReminderModel)->Unit,
    reminderModel: ReminderModel
) {
    var contentUpdate by remember { mutableStateOf(reminderModel.content)}
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
                .border(2.dp, color = Color(0xFF163C5D), shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(value = contentUpdate,
                    onValueChange = { contentUpdate = it  },
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                    label = { Text(text = "Contenido") },
                    supportingText = {
                        if (contentUpdate.isNullOrEmpty() || contentUpdate.contains(" ")) {
                            Text(text = "Ingrese el recordatorio")
                        }
                    })
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
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
                            .weight(1f)
                        ,
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
                            reminderModel.apply { content = contentUpdate }
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


@Composable
fun CustomDialogPrograming(
    onDismiss:()->Unit,
    onConfirm:(ReminderModel)->Unit,
    reminderModel: ReminderModel
) {
    var contentUpdate by remember { mutableStateOf(reminderModel.content)}
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
                .border(2.dp, color = Color(0xFF163C5D), shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(value = contentUpdate,
                    onValueChange = { contentUpdate = it  },
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                    label = { Text(text = "Contenido") },
                    supportingText = {
                        if (contentUpdate.isNullOrEmpty() || contentUpdate.contains(" ")) {
                            Text(text = "Ingrese el recordatorio")
                        }
                    })
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
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
                            .weight(1f)
                        ,
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
                            reminderModel.apply { content = contentUpdate }
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


