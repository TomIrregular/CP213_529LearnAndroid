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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun SummaryScreen(
    currentDay: Int,
    dailyEarnings: Int,
    dailyQuota: Int,
    totalMoney: Int,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(MediumBrown, DarkBrown)
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
                            LightBrown,
                            MediumBrown
                        )
                    )
                )
                .border(3.dp, Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🎉 DAY $currentDay COMPLETE!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Gold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    color = Gold.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                SummaryRow("💰 Earned", "$$dailyEarnings")
                SummaryRow("📊 Quota", "$$dailyQuota")
                SummaryRow("✅ Status", "PASSED!", MoneyGreen)

                HorizontalDivider(
                    color = Gold.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                SummaryRow("🏦 Total Money", "$$totalMoney")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmOrange,
                        contentColor = DarkBrown
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "CONTINUE ▶",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
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
