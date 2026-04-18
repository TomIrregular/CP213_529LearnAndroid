package com.tomweasley.overgrilled.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun MainMenuScreen(
    highScore: Int,
    onStartClick: () -> Unit,
    onOptionClick: () -> Unit
) {
    // Subtle flame animation for the title glow
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBrown, MediumBrown, DarkBrown)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Left side: Title + High Score + Buttons ──
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title placeholder box
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(2.5f)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = WarmOrange.copy(alpha = glowAlpha),
                            spotColor = WarmOrange.copy(alpha = glowAlpha)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    WarmOrange,
                                    Color(0xFFFF8F00),
                                    Gold
                                )
                            )
                        )
                        .border(
                            width = 3.dp,
                            color = Gold.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🥩 OVERGRILLED",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkBrown,
                            letterSpacing = 3.sp
                        )
                        Text(
                            text = "Tom Weasley's",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkBrown.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // High Score
                Text(
                    text = "🏆 HIGH SCORE: $$highScore",
                    style = MaterialTheme.typography.titleLarge,
                    color = Gold,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                // START button
                Button(
                    onClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmOrange,
                        contentColor = DarkBrown
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "▶  START",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // OPTION button (does nothing)
                OutlinedButton(
                    onClick = onOptionClick,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = CreamWhite.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp, CreamWhite.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        text = "⚙  OPTION",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(24.dp))

            // ── Right side: Decorative image placeholder ──
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .aspectRatio(0.85f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                LightBrown,
                                MediumBrown
                            )
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = WarmOrange.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🔥",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "IMAGE\nPLACEHOLDER",
                        color = CreamWhite.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
