package com.applicationtls.tools.ui.qrscan

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.applicationtls.tools.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun QrScanScreen() {
    var resultadoScan by remember { mutableStateOf("")}
    val launcher = rememberLauncherForActivityResult(contract = ScanContract(), onResult = { result->
        resultadoScan = result.contents?:"Información no encontrada"
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    )
    {
        Card(modifier = Modifier.clickable { launcher.launch(ScanOptions()) }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ){
                Icon(painter = painterResource(id = R.drawable.baseline_qr_code_24),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp ))
                Text(text = "Escanear Codigo")
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Card(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .background(Color(0xFFD9F6FF))
                .border(
                    width = 4.dp, color = Color(0xFF290569)
                )
        ) {
            Text(text = if(resultadoScan.isNullOrEmpty())"Resultado" else resultadoScan,
                modifier = Modifier.padding(15.dp))
        }
    }
}
