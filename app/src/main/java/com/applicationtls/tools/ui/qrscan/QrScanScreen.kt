package com.applicationtls.tools.ui.qrscan

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun QrScanScreen(navController: NavHostController) {
    var resultadoScan by remember { mutableStateOf("")}
    val launcher = rememberLauncherForActivityResult(contract = ScanContract(), onResult = { result->
        resultadoScan = result.contents?:"Data not Found"
    })
    Row(
        modifier = Modifier.fillMaxSize(),
    )
    {
        Button(onClick = {launcher.launch(ScanOptions())}) {
            Text(text = "Escanear Codigo")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Result: $resultadoScan")
    }
}
