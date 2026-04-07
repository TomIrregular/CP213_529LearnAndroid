package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Part11Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Skeleton Loading (Part 11)", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Concept of Skeleton Loading:\n\n" +
                                    "Skeleton Loading คือเทคนิคการแสดงผล UI จำลองที่มีรูปร่าง (โครงกระดูก) คล้ายคลึงกับเนื้อหาจริงที่จะตามมาในขณะที่แอปกำลังโหลดข้อมูลจากเซิร์ฟเวอร์หรือฐานข้อมูล " +
                                    "ช่วยลดความรู้สึกของการรอคอย ทำให้ผู้ใช้รู้สึกว่าแอพตอบสนองรวดเร็วกว่าการใช้ตัวโหลดสปินเนอร์ธรรมดา (Progress Bar)\n\n" +
                                    "ใน Compose เราสามารถสร้าง Effect การโหลดกระพริบ (Shimmer effect) โดยการทำ Animation เปลี่ยนค่า Alpha (ความโปร่งใส) ของกล่องสีเทาไปมาได้",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Skeleton items
                        repeat(3) {
                            SkeletonItem()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkeletonItem() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Gray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = alpha))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(15.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = alpha))
            )
        }
    }
}
