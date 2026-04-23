package com.tomweasley.overgrilled.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tomweasley.overgrilled.R
import com.tomweasley.overgrilled.data.GifImage // Import the GIF loader
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun MainMenuScreen(
    highScore: Int,
    isMusicEnabled: Boolean,
    onStartClick: () -> Unit,
    onToggleMusic: () -> Unit
) {
    var showOptionsDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. --- BACKGROUND LAYER (Handling the GIF) ---
        GifImage(
            resourceId = R.drawable.main_menu, // main_menu.gif
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Keep the background scaled correctly
        )

        // 2. --- CONTENT LAYER ---
        // Using a Row with a Spacer creates a two-column effect,
        // pushing the actual UI components to the left side.
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp, vertical = 64.dp) // Pushing content from edges
        ) {
            // Left Column (Title + Score + Buttons)
            Column(
                modifier = Modifier.weight(0.5f)
                    .fillMaxHeight(), // Takes up 50% of the horizontal space
                horizontalAlignment = Alignment.Start, // EVERYTHING ALIGN LEFT
                verticalArrangement = Arrangement.Bottom // Pushes everything down to the bottom
            ) {
                // RESTORING TITLE IMAGE
                Image(
                    painter = painterResource(id = R.drawable.title), // Use your actual title image asset
                    contentDescription = "Overgrilled",
                    modifier = Modifier.width(280.dp).wrapContentHeight(),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "HIGH SCORE: $$highScore",
                    color = MoneyGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 12.dp) // Slight shift to align with button text
                )

                Spacer(modifier = Modifier.height(3.dp))
                HorizontalDivider(
                    modifier = Modifier.width(300.dp).padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = MediumBrown
                )
                Spacer(modifier = Modifier.height(3.dp))


                Button(
                    onClick = onStartClick,
                    modifier = Modifier.width(200.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WarmOrange)
                ) {
                    Text(
                        "START SHIFT",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Options Button
                Button(
                    onClick = { showOptionsDialog = true },
                    modifier = Modifier.width(200.dp).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MediumBrown)
                ) {
                    Text(
                        "OPTIONS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CreamWhite
                    )
                }
            }

            // Right Side Spacer (Creates space so the busy background isn't as distracting)
            Spacer(modifier = Modifier.weight(0.5f))
        }

        // --- Options Popup ---
        if (showOptionsDialog) {
            Dialog(onDismissRequest = { showOptionsDialog = false }) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(LightBrown, RoundedCornerShape(16.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "OPTIONS",
                            color = DarkBrown,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Music", color = DarkBrown, fontWeight = FontWeight.Bold)
                            Switch(
                                checked = isMusicEnabled,
                                onCheckedChange = { onToggleMusic() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = CreamWhite,
                                    checkedTrackColor = MoneyGreen,
                                    uncheckedThumbColor = DarkBrown,
                                    uncheckedTrackColor = DisabledGray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { showOptionsDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = WarmOrange)
                        ) {
                            Text("CLOSE", color = DarkBrown, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}