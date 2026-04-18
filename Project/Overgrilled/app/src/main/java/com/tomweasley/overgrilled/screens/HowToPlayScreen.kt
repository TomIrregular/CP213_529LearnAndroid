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
fun HowToPlayScreen(onNextClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBrown, MediumBrown)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Full-screen how-to-play placeholder
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(LightBrown.copy(alpha = 0.5f))
                    .border(
                        width = 2.dp,
                        color = WarmOrange.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "📖 HOW TO PLAY",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Gold,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val instructions = listOf(
                        "1. 👤  A customer will appear and tell you their order",
                        "2. 🥩  Tap a MEAT button to place it on the grill",
                        "3. 🔥  HOLD the grill button — release at the right time!",
                        "     🟢 Rare  |  🟡 Well Done  |  🔴 Overgrilled",
                        "4. 🫙  Add a SIDE (sauce or potato) if ordered",
                        "5. 📤  Hit SEND to serve — or 🗑 TRASH to start over",
                        "6. 💰  Earn $20 per correct part (meat, grill, side)",
                        "7. ⏱  You have 3 MINUTES per day to meet the quota!"
                    )

                    instructions.forEach { line ->
                        Text(
                            text = line,
                            fontSize = 15.sp,
                            color = CreamWhite,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(0.85f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // NEXT button
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmOrange,
                    contentColor = DarkBrown
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "NEXT  ▶",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}
