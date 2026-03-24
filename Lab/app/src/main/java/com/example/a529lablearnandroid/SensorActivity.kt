package com.example.a529lablearnandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SensorData(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f, val lat: Double = 0.0, val lon: Double = 0.0)

class SensorViewModel : ViewModel() {
    private val _sensorData = MutableStateFlow(SensorData())
    val sensorData: StateFlow<SensorData> = _sensorData.asStateFlow()

    fun updateSensorData(x: Float, y: Float, z: Float) {
        _sensorData.value = _sensorData.value.copy(x = x, y = y, z = z)
    }

    fun updateLocation(lat: Double, lon: Double) {
        _sensorData.value = _sensorData.value.copy(lat = lat, lon = lon)
    }
}

class SensorActivity : ComponentActivity(), SensorEventListener, LocationListener {

    private val viewModel: SensorViewModel by viewModels()
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        setContent {
            val sensorData by viewModel.sensorData.collectAsState()

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
                val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
                if (fineLocationGranted || coarseLocationGranted) {
                    startLocationUpdates()
                }
            }

            LaunchedEffect(Unit) {
                if (ContextCompat.checkSelfPermission(this@SensorActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this@SensorActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationPermissionLauncher.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                } else {
                    startLocationUpdates()
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sensor & Location View",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text("Accelerometer:", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Text("X: ${"%.2f".format(sensorData.x)}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text("Y: ${"%.2f".format(sensorData.y)}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text("Z: ${"%.2f".format(sensorData.z)}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

                Text("Location:", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Text("Lat: ${sensorData.lat}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text("Lon: ${sensorData.lon}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        // Start location updates again if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        locationManager.removeUpdates(this)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000L,
                1f,
                this
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                2000L,
                1f,
                this
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            viewModel.updateSensorData(event.values[0], event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onLocationChanged(location: Location) {
        viewModel.updateLocation(location.latitude, location.longitude)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Unit"))
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}
