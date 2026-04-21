package com.tomweasley.overgrilled.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.tomweasley.overgrilled.R
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun MainMenuScreen(
    highScore: Int,
    onStartClick: () -> Unit,
    onOptionClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

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

    // Wrap the entire screen in AnimatedVisibility
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut()
    ) {
        // Adding background(DarkBrown) here prevents any flickering
        Box(modifier = Modifier.fillMaxSize().background(DarkBrown)) {

            // ── Animated GIF Background ──
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.main_menu)
                    .decoderFactory(
                        if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory()
                        else GifDecoder.Factory()
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // ── Content ──
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.wrapContentWidth(Alignment.Start).padding(start = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Title image
                    Image(
                        painter = painterResource(id = R.drawable.title),
                        contentDescription = "Overgrilled Title",
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = WarmOrange.copy(alpha = glowAlpha),
                                spotColor = WarmOrange.copy(alpha = glowAlpha)
                            ),
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // High Score (Safe syntax)
                    Text(
                        text = "\uD83C\uDFC6 HIGH SCORE: $${highScore}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF1F6425),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // START button
                    Button(
                        onClick = onStartClick,
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MoneyGreen,
                            contentColor = DarkBrown
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 2.dp
                        )
                    ) {
                        Text(
                            text = "▶ START",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                        )
                    }
                }
            }
        }
    }
}
