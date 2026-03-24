package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SensorActivity : ComponentActivity() {

    private lateinit var sensorTracker: SensorTracker
    private val viewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorTracker = SensorTracker(this)

        setContent {
            val sensorData by viewModel.sensorData.collectAsState()

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Accelerometer View",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text("X: ${"%.2f".format(sensorData.x)}", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text("Y: ${"%.2f".format(sensorData.y)}", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
                Text("Z: ${"%.2f".format(sensorData.z)}", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorTracker.startListening { x, y, z ->
            viewModel.updateSensorData(x, y, z)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorTracker.stopListening()
    }
}
