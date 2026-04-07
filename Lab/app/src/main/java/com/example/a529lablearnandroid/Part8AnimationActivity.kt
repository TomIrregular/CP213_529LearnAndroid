package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Part8AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResponsiveProfileLayout()
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileLayout() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (maxWidth < 600.dp) {
            // Portrait / Mobile: Column layout
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfilePicture()
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo()
            }
        } else {
            // Landscape / Tablet: Row layout
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    ProfilePicture()
                }
                Spacer(modifier = Modifier.width(24.dp))
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    ProfileInfo()
                }
            }
        }
    }
}

@Composable
fun ProfilePicture() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "รูปโปรไฟล์", color = Color.White)
    }
}

@Composable
fun ProfileInfo() {
    Column {
        Text(text = "ข้อมูลส่วนตัว", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ชื่อ: สมมติ นามสกุล", style = MaterialTheme.typography.bodyLarge)
        Text(text = "อาชีพ: นักพัฒนาซอฟต์แวร์", style = MaterialTheme.typography.bodyLarge)
        Text(text = "ความสนใจ: Android, Kotlin, Compose", style = MaterialTheme.typography.bodyLarge)
    }
}
