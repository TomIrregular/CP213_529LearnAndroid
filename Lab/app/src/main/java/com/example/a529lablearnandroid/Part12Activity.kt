package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class Part12Activity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var showBottomSheet by remember { mutableStateOf(false) }
                    var showDialog by remember { mutableStateOf(false) }
                    val sheetState = rememberModalBottomSheetState()
                    
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Modal Bottom Sheet & Dialog (Part 12)", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Concept:\n\n" +
                                    "1. Modal Bottom Sheet: คือแผงที่เลื่อนขึ้นมาจากด้านล่างของหน้าจอ มักใช้ครอบทับเนื้อหาหลักเพื่อให้ผู้ใช้สามารถมีปฏิสัมพันธ์หรือนำเสนอเมนูทางเลือกได้โดยไม่ต้องออกจากหน้าปัจจุบัน (ใน Compose ใช้ ModalBottomSheet)\n\n" +
                                    "2. Middle Dialog: คือกล่องข้อความโต้ตอบที่โผล่ขึ้นมาตรงกลางหน้าจอ เพื่อขัดจังหวะการทำงานและบังคับให้ผู้ใช้ตัดสินใจ แจ้งเตือน หรือยืนยันคำสั่ง (ใน Compose ใช้ AlertDialog หรือ Dialog) ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Button(onClick = { showBottomSheet = true }) {
                            Text("Open Bottom Sheet")
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(onClick = { showDialog = true }) {
                            Text("Open Middle Dialog")
                        }
                    }

                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showBottomSheet = false },
                            sheetState = sheetState
                        ) {
                            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                                Text(text = "This is a Modal Bottom Sheet", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "มันลอยขึ้นมาจากด้านล่าง และสามารถปัดลง (Swipe down) หรือครอบทับเนื้อหาด้านข้างบนได้ เพื่อปิดได้")
                                Spacer(modifier = Modifier.height(32.dp))
                                Button(onClick = { showBottomSheet = false }) {
                                    Text("Close")
                                }
                            }
                        }
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = "Middle Dialog") },
                            text = { Text(text = "นี่คือ Dialog ที่แสดงตรงกลางหน้าจอ มักใช้สำหรับการยืนยัน หรือแจ้งเตือนสำคัญ") },
                            confirmButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
