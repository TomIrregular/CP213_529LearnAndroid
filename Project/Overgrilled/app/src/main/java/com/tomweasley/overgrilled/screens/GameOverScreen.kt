package com.tomweasley.overgrilled.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomweasley.overgrilled.R
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun GameOverScreen(
    currentDay: Int,
    dailyEarnings: Int,
    dailyQuota: Int,
    totalMoney: Int,
    // highScore: Int, // Removed as requested
    onBackToMenu: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // --- BACKGROUND ---
        Image(
            painter = painterResource(id = R.drawable.summary_screen),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // --- CONTENT OVERLAY ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp)
                .padding(top = 80.dp, bottom = 32.dp)
        ) {
            // Header
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Day $currentDay FAILED!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF8B0000), // Dark ink red
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(end = 40.dp)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Main Data Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                // LEFT SIDE: Stats List
                Column(
                    modifier = Modifier.width(220.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TransparentStatRow("Day Earnings", "$$dailyEarnings", Color(0xFF4E2020))

                    // Row for Quota and Short By
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically // Standard alignment for standardized size
                    ) {
                        // Standardized font size for correct inline look
                        TransparentStatRow("Day Quota", "$$dailyQuota", Color(0xFF4E2020))

                        Spacer(modifier = Modifier.width(24.dp)) // A bit more spacing looks better when sizes match

                        val shortBy = (dailyQuota - dailyEarnings).coerceAtLeast(0)

                        // "Short by" is now its own distinct TransparentStatRow,
                        // ensuring labels and values match sizes perfectly.
                        TransparentStatRow("SHORT BY", "-$$shortBy", OvergrilledRed)
                    }

                    HorizontalDivider(
                        modifier = Modifier.width(120.dp).padding(vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color(0xFF4E2020).copy(alpha = 0.2f)
                    )

                    TransparentStatRow("Total Money", "$$totalMoney", Gold)
                    // High Score removed
                }

                Spacer(modifier = Modifier.width(0.dp))

                // RIGHT SIDE: Action Button
                Column(
                    modifier = Modifier.width(130.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(45.dp))
                    Button(
                        onClick = onBackToMenu,
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OvergrilledRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("QUIT", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransparentStatRow(
    label: String,
    value: String,
    valueColor: Color
) {
    Column {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.4f),
            letterSpacing = 0.5.sp
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = valueColor
        )
    }
}