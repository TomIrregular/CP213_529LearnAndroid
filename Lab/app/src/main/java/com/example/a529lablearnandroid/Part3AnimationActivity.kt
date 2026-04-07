package com.example.a529lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.a529lablearnandroid.ui.theme._529LabLearnAndroidTheme

class Part3AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _529LabLearnAndroidTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutChart(
                            proportions = listOf(30f, 40f, 30f),
                            colors = listOf(
                                Color(0xFFFF6B6B),
                                Color(0xFF4ECDC4),
                                Color(0xFFFFD93D)
                            ),
                            modifier = Modifier.size(250.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    var animationPlayed by remember { mutableStateOf(false) }
    
    // Sweep Animate จาก 0 ถึง 360 องศา
    val currentSweepAngle by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing),
        label = "sweep_angle"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    val total = proportions.sum()
    if (total == 0f) return

    Canvas(modifier = modifier) {
        var startAngle = -90f // เริ่มวาดจากด้านบนสุด
        var totalSweepSoFar = 0f

        for (i in proportions.indices) {
            val sweep = (proportions[i] / total) * 360f
            
            // คำนวณหาว่าวาดส่วนโค้งมาถึงระดับใดสำหรับการทำ Animation
            val exposedSweep = (currentSweepAngle - totalSweepSoFar).coerceIn(0f, sweep)
            
            if (exposedSweep > 0f) {
                drawArc(
                    color = colors.getOrElse(i) { Color.Gray },
                    startAngle = startAngle,
                    sweepAngle = exposedSweep,
                    useCenter = false,
                    style = Stroke(width = 60.dp.toPx(), cap = StrokeCap.Butt)
                )
            }
            
            startAngle += sweep
            totalSweepSoFar += sweep
        }
    }
}
