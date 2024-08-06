package com.applicationtls.tools.ui.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.applicationtls.tools.R
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopBar() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Reminder App Utils:",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.bbsans)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(100),
                        color = Color(0xFA00114D)
                    ),
                    modifier = Modifier.padding(10.dp)
                )
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .size(40.dp)
                    .clickable {
                        navController.navigate(QrScan)
                        scope.launch { drawerState.close() }
                    }) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Spacer(modifier = Modifier.size(10.dp))
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "")
                        Text(text = "Lector QR")
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Reminder)
                        scope.launch { drawerState.close() }
                    }) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Spacer(modifier = Modifier.size(10.dp))
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "")
                        Text(text = "Recordatorios")
                    }
                }
            }
        },
    ) {
        Scaffold(floatingActionButton = {
            ExtendedFloatingActionButton(text = { Text("Menu") },
                icon = { Icon(Icons.Filled.Menu, contentDescription = "") },
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
        }) { contentPadding ->
            MainNavigation(navController)
        }
    }
}