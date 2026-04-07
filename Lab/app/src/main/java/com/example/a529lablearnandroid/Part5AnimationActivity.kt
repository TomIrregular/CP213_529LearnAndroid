package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a529lablearnandroid.ui.theme._529LabLearnAndroidTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// 1. สร้าง ViewModel ที่มี Channel สำหรับส่งข้อความ Error (One-time event) แบบ Side Effect
class SnackbarViewModel : ViewModel() {
    private val _errorChannel = Channel<String>()
    
    // receiveAsFlow เพื่อให้ UI ตามเก็บ (collect) Event ได้ โดยจะไม่เก็บค่าซ้ำแบบ StateFlow
    val errorFlow = _errorChannel.receiveAsFlow()

    // ฟังก์ชันจำลองการเกิด Error
    fun triggerError() {
        viewModelScope.launch {
            _errorChannel.send("Error เกิดขึ้นแล้ว! ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้")
        }
    }
}

class Part5AnimationActivity : ComponentActivity() {
    private val viewModel by viewModels<SnackbarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _529LabLearnAndroidTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                // 3. ใช้ LaunchedEffect เพื่อ Observe ค่า Error จาก ViewModel (Side Effect)
                LaunchedEffect(Unit) {
                    viewModel.errorFlow.collect { errorMessage ->
                        // หากมี event error มา ให้แสดง Snackbar
                        snackbarHostState.showSnackbar(
                            message = errorMessage
                        )
                    }
                }

                // 2. ใช้ Scaffold และมี SnackbarHost
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        // 4. สร้างปุ่ม Trigger Error
                        Button(onClick = { viewModel.triggerError() }) {
                            Text("Trigger Error")
                        }
                    }
                }
            }
        }
    }
}
