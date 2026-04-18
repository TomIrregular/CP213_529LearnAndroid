package com.tomweasley.overgrilled.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomweasley.overgrilled.data.*
import com.tomweasley.overgrilled.ui.theme.*

@Composable
fun GameScreen(
    state: GameState,
    onSelectMeat: (MeatType) -> Unit,
    onStartGrill: () -> Unit,
    onStopGrill: () -> Unit,
    onSelectSide: (SideCondiment) -> Unit,
    onSend: () -> Unit,
    onTrash: () -> Unit
) {
    val minutes = (state.timeRemainingMs / 60000).toInt()
    val seconds = ((state.timeRemainingMs % 60000) / 1000).toInt()
    val timerColor = if (state.timeRemainingMs < 30_000L) OvergrilledRed else CreamWhite

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        // ── HUD bar ─────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MediumBrown)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📅 Day ${state.currentDay}",
                color = CreamWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "⏱ %d:%02d".format(minutes, seconds),
                color = timerColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
            Text(
                text = "💰 $${state.dailyEarnings} / $${state.dailyQuota}",
                color = if (state.dailyEarnings >= state.dailyQuota) MoneyGreen else Gold,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // ── Main game area ──────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ═══ LEFT COLUMN: Character + Speech ═══
            LeftColumn(
                state = state,
                modifier = Modifier.weight(0.35f)
            )

            // ═══ CENTER COLUMN: Meat buttons + Side buttons ═══
            CenterColumn(
                state = state,
                onSelectMeat = onSelectMeat,
                onSelectSide = onSelectSide,
                modifier = Modifier.weight(0.25f)
            )

            // ═══ RIGHT COLUMN: Grill + Dish + Send/Trash ═══
            RightColumn(
                state = state,
                onStartGrill = onStartGrill,
                onStopGrill = onStopGrill,
                onSend = onSend,
                onTrash = onTrash,
                modifier = Modifier.weight(0.40f)
            )
        }
    }
}

// ────────────────────────────────────────────────────────────────────────
// LEFT COLUMN
// ────────────────────────────────────────────────────────────────────────

@Composable
private fun LeftColumn(state: GameState, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Character image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBrown)
                    .border(2.dp, WarmOrange.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "👤",
                        fontSize = 48.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.currentCharacter?.name ?: "...",
                        color = CreamWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "CHARACTER",
                        color = CreamWhite.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Speech box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF4E342E),
                                Color(0xFF3E2723)
                            )
                        )
                    )
                    .border(2.dp, Gold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Text(
                        text = "💬 ${state.currentCharacter?.name ?: ""}",
                        color = Gold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.dialogueWords.joinToString(" "),
                        color = CreamWhite,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────────────
// CENTER COLUMN
// ────────────────────────────────────────────────────────────────────────

@Composable
private fun CenterColumn(
    state: GameState,
    onSelectMeat: (MeatType) -> Unit,
    onSelectSide: (SideCondiment) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Meat buttons (2×2 grid)
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            val meats = listOf(
                MeatType.BEEF to BeefBrown,
                MeatType.CHICKEN to ChickenOrange,
                MeatType.PORK to PorkPink,
                MeatType.FISH to FishBlue
            )

            meats.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { (meat, color) ->
                        val isSelected = state.selectedMeat == meat
                        val isDisabled = state.meatCooked

                        Button(
                            onClick = { onSelectMeat(meat) },
                            enabled = !isDisabled,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1.2f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) color else color.copy(alpha = 0.6f),
                                contentColor = DarkBrown,
                                disabledContainerColor = DisabledGray.copy(alpha = 0.3f),
                                disabledContentColor = DisabledGray
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = if (isSelected) 8.dp else 2.dp
                            ),
                            border = if (isSelected) {
                                androidx.compose.foundation.BorderStroke(3.dp, Gold)
                            } else null
                        ) {
                            Text(
                                text = meat.displayName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Side buttons (SAUCE + POTATO)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            SideButton(
                label = "Sauce",
                emoji = "🫙",
                color = SauceRed,
                isSelected = state.selectedSide == SideCondiment.SAUCE,
                onClick = { onSelectSide(SideCondiment.SAUCE) },
                modifier = Modifier.weight(1f)
            )
            SideButton(
                label = "Potato",
                emoji = "🥔",
                color = PotatoYellow,
                isSelected = state.selectedSide == SideCondiment.POTATO,
                onClick = { onSelectSide(SideCondiment.POTATO) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SideButton(
    label: String,
    emoji: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.4f),
            contentColor = if (isSelected) DarkBrown else CreamWhite
        ),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, Gold)
        } else null
    ) {
        Text(
            text = "$emoji $label",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

// ────────────────────────────────────────────────────────────────────────
// RIGHT COLUMN
// ────────────────────────────────────────────────────────────────────────

@Composable
private fun RightColumn(
    state: GameState,
    onStartGrill: () -> Unit,
    onStopGrill: () -> Unit,
    onSend: () -> Unit,
    onTrash: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // ── Grill area ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF424242),
                            Color(0xFF212121)
                        )
                    )
                )
                .border(2.dp, Color(0xFF616161), RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Grill image placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF333333)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (state.selectedMeat != null) "🔥" else "⬜",
                            fontSize = 36.sp
                        )
                        if (state.selectedMeat != null) {
                            Text(
                                text = state.selectedMeat.displayName,
                                color = CreamWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if (state.grilledLevel != null) {
                                Text(
                                    text = "(${state.grilledLevel.displayName})",
                                    color = when (state.grilledLevel) {
                                        GrillLevel.RARE -> RareGreen
                                        GrillLevel.WELL_DONE -> WellDoneAmber
                                        GrillLevel.OVERGRILLED -> OvergrilledRed
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        } else {
                            Text(
                                text = "GRILL",
                                color = CreamWhite.copy(alpha = 0.4f),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // ── Grill bar (inside grill area) ───────────────
                GrillBar(
                    progress = state.grillProgress,
                    isGrilling = state.isGrilling,
                    meatCooked = state.meatCooked,
                    hasMeat = state.selectedMeat != null,
                    onStartGrill = onStartGrill,
                    onStopGrill = onStopGrill
                )
            }
        }

        // ── Bottom row: Dish result + Send/Trash ────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Dish result
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBrown.copy(alpha = 0.6f))
                    .border(2.dp, WarmOrange.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.meatCooked && state.selectedMeat != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🍽️ DISH",
                            color = Gold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = buildString {
                                append(state.grilledLevel?.displayName ?: "")
                                append(" ")
                                append(state.selectedMeat.displayName)
                                if (state.selectedSide != SideCondiment.NONE) {
                                    append("\n+ ${state.selectedSide.displayName}")
                                }
                            },
                            color = CreamWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        text = "DISH\nRESULT",
                        color = CreamWhite.copy(alpha = 0.3f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Send / Trash buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                Button(
                    onClick = onSend,
                    modifier = Modifier
                        .width(72.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MoneyGreen,
                        contentColor = DarkBrown
                    ),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "SEND",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = onTrash,
                    modifier = Modifier
                        .width(72.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TrashGray,
                        contentColor = CreamWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "TRASH",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

// ────────────────────────────────────────────────────────────────────────
// GRILL BAR
// ────────────────────────────────────────────────────────────────────────

@Composable
private fun GrillBar(
    progress: Float,
    isGrilling: Boolean,
    meatCooked: Boolean,
    hasMeat: Boolean,
    onStartGrill: () -> Unit,
    onStopGrill: () -> Unit
) {
    val canGrill = hasMeat && !meatCooked
    val barAlpha = if (canGrill) 1f else 0.3f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Bar with 3 colored zones
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .then(
                    if (canGrill) {
                        Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    onStartGrill()
                                    tryAwaitRelease()
                                    onStopGrill()
                                }
                            )
                        }
                    } else Modifier
                )
        ) {
            // 3 colored zone background
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(RareGreen.copy(alpha = barAlpha))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(WellDoneAmber.copy(alpha = barAlpha))
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(OvergrilledRed.copy(alpha = barAlpha))
                )
            }

            // Zone divider lines
            Row(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(Color.White.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(Color.White.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // Arrow indicator
            if (progress > 0f || isGrilling) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val offsetX = maxWidth * progress
                    Box(
                        modifier = Modifier
                            .offset(x = offsetX - 3.dp)
                            .width(6.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.White)
                            .border(1.dp, DarkBrown, RoundedCornerShape(3.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Zone labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Rare", color = RareGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("Well Done", color = WellDoneAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("Over!", color = OvergrilledRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }

        if (canGrill) {
            Text(
                text = if (isGrilling) "🔥 Release to stop!" else "Hold to COOK",
                color = if (isGrilling) WarmOrange else CreamWhite.copy(alpha = 0.6f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
