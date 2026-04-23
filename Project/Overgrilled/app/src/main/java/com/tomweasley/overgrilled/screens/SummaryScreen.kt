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
import com.tomweasley.overgrilled.data.GifImage
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun SummaryScreen(
    currentDay: Int,
    dailyEarnings: Int,
    dailyQuota: Int,
    totalMoney: Int,
    onContinue: () -> Unit,
    onMainMenu: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // --- BACKGROUND ---
        GifImage(
            resourceId = R.drawable.summary_screen,
            modifier = Modifier.fillMaxSize()
        )

        // --- CONTENT OVERLAY ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp)
                .padding(top = 80.dp, bottom = 32.dp) // Pushed everything down from the top
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Using weight pushes the text as far right as possible
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Day $currentDay passed!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF8B0000),
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(end = 40.dp) // Keeps it from hitting the very edge
                )
            }

            // Increased this height to bring the stats/buttons down further
            Spacer(modifier = Modifier.height(60.dp))

            // Main Data Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LEFT SIDE: Stats
                Column(
                    modifier = Modifier.width(160.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TransparentStatRow("Earned", "$$dailyEarnings", Color(0xFF4E2020))
                    TransparentStatRow("Quota", "$$dailyQuota", Color(0xFF4E2020))

                    HorizontalDivider(
                        modifier = Modifier.width(100.dp),
                        thickness = 1.dp,
                        color = Color(0xFF4E2020)
                    )

                    TransparentStatRow("Total Savings", "$$totalMoney", Gold)
                }

                Spacer(modifier = Modifier.width(38.dp)) // Added space between stats and buttons

                // RIGHT SIDE: Buttons
                Column(
                    modifier = Modifier.width(130.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onContinue,
                        modifier = Modifier.fillMaxWidth().height(44.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MoneyGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("NEXT ▶", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    Button(
                        onClick = onMainMenu,
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
            fontSize = 11.sp, // Smaller label
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.4f),
            letterSpacing = 0.5.sp
        )
        Text(
            text = value,
            fontSize = 24.sp, // Scaled down from 32
            fontWeight = FontWeight.Black,
            color = valueColor
        )
    }
}