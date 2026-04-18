package com.tomweasley.overgrilled.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun GameOverScreen(
    currentDay: Int,
    dailyEarnings: Int,
    dailyQuota: Int,
    totalMoney: Int,
    highScore: Int,
    onBackToMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF3E1A1A),
                        DarkBrown
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4E2020),
                            MediumBrown
                        )
                    )
                )
                .border(3.dp, OvergrilledRed.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "💀 GAME OVER",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = OvergrilledRed,
                    letterSpacing = 3.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "You couldn't meet the quota!",
                    color = CreamWhite.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )

                HorizontalDivider(
                    color = OvergrilledRed.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                GameOverRow("📅 Day", "$currentDay")
                GameOverRow("💰 Day Earnings", "$$dailyEarnings")
                GameOverRow("📊 Day Quota", "$$dailyQuota")
                GameOverRow("❌ Short by", "$${(dailyQuota - dailyEarnings).coerceAtLeast(0)}", OvergrilledRed)

                HorizontalDivider(
                    color = OvergrilledRed.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                GameOverRow("🏦 Total Money", "$$totalMoney")
                GameOverRow("🏆 High Score", "$$highScore", Gold)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onBackToMenu,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OvergrilledRed.copy(alpha = 0.8f),
                        contentColor = CreamWhite
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "BACK TO MENU",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun GameOverRow(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = CreamWhite
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = CreamWhite.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
