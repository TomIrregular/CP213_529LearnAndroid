package com.tomweasley.overgrilled.screens

import android.os.Build
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.tomweasley.overgrilled.R
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

    Box(modifier = Modifier.fillMaxSize()) {
        GifImage(
            resourceId = R.drawable.main_menu,
            modifier = Modifier.fillMaxSize()
        )

        Box(modifier = Modifier.fillMaxSize().background(DarkBrown.copy(alpha = 0.6f)))

        Column(modifier = Modifier.fillMaxSize()) {
            // HUD
            Row(
                modifier = Modifier.fillMaxWidth().background(MediumBrown.copy(alpha = 0.9f)).padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Day ${state.currentDay}", color = CreamWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("⏱ %d:%02d".format(minutes, seconds), color = timerColor, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Text("$${state.dailyEarnings} / $${state.dailyQuota}", color = if (state.dailyEarnings >= state.dailyQuota) MoneyGreen else Gold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Row(modifier = Modifier.fillMaxSize().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LeftColumn(state = state, modifier = Modifier.weight(0.35f))
                CenterColumn(state = state, onSelectMeat = onSelectMeat, onSelectSide = onSelectSide, modifier = Modifier.weight(0.25f))
                RightColumn(state = state, onStartGrill = onStartGrill, onStopGrill = onStopGrill, onSend = onSend, onTrash = onTrash, modifier = Modifier.weight(0.40f))
            }
        }
    }
}

// ── DRAWABLE HELPERS ──────────────────────────────────────────────────

private fun getCustomerGif(name: String?): Int {
    return when (name) {
        "Science" -> R.drawable.customer_sc
        "Humanity" -> R.drawable.customer_hm
        "Fofa" -> R.drawable.customer_fofa
        else -> R.drawable.customer_sc // Default fallback
    }
}

private fun getMeatSelectDrawable(meat: MeatType): Int = when (meat) {
    MeatType.BEEF    -> R.drawable.select_beef
    MeatType.CHICKEN -> R.drawable.select_chicken
    MeatType.PORK    -> R.drawable.select_pork
    MeatType.FISH    -> R.drawable.select_fish
}

private fun getSideSelectDrawable(side: SideCondiment): Int = when (side) {
    SideCondiment.SAUCE  -> R.drawable.select_sauce
    SideCondiment.POTATO -> R.drawable.select_potato
    else                 -> 0
}

private fun getGrillMeatDrawable(meat: MeatType, isCooking: Boolean): Int = when (meat) {
    MeatType.BEEF    -> if (isCooking) R.drawable.grill_beef1    else R.drawable.grill_beef
    MeatType.CHICKEN -> if (isCooking) R.drawable.grill_chicken1 else R.drawable.grill_chicken
    MeatType.PORK    -> if (isCooking) R.drawable.grill_pork1    else R.drawable.grill_pork
    MeatType.FISH    -> if (isCooking) R.drawable.grill_fish1    else R.drawable.grill_fish
}

private fun getDishDrawable(meat: MeatType, level: GrillLevel, side: SideCondiment): Int {
    return when (meat) {
        MeatType.BEEF -> when (side) {
            SideCondiment.NONE   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_beef_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_beef_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_beef_overgrilled
            }
            SideCondiment.SAUCE  -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_beef_sauce_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_beef_sauce_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_beef_sauce_overgrilled
            }
            SideCondiment.POTATO -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_beef_potato_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_beef_potato_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_beef_potato_overgrilled
            }
            SideCondiment.BOTH   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_beef_full_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_beef_full_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_beef_full_overgrilled
            }
        }
        MeatType.CHICKEN -> when (side) {
            SideCondiment.NONE   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_chicken_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_chicken_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_chicken_overgrilled
            }
            SideCondiment.SAUCE  -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_chicken_sauce_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_chicken_sauce_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_chicken_sauce_overgrilled
            }
            SideCondiment.POTATO -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_chicken_potato_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_chicken_potato_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_chicken_potato_overgrilled
            }
            SideCondiment.BOTH   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_chicken_full_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_chicken_full_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_chicken_full_overgrilled
            }
        }
        MeatType.PORK -> when (side) {
            SideCondiment.NONE   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_pork_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_pork_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_pork_overgrilled
            }
            SideCondiment.SAUCE  -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_pork_sauce_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_pork_sauce_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_pork_sauce_overgrilled
            }
            SideCondiment.POTATO -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_pork_potato_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_pork_potato_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_pork_potato_overgrilled
            }
            SideCondiment.BOTH   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_pork_full_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_pork_full_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_pork_full_overgrilled
            }
        }
        MeatType.FISH -> when (side) {
            SideCondiment.NONE   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_fish_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_fish_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_fish_overgrilled
            }
            SideCondiment.SAUCE  -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_fish_sauce_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_fish_sauce_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_fish_sauce_overgrilled
            }
            SideCondiment.POTATO -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_fish_potato_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_fish_potato_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_fish_potato_overgrilled
            }
            SideCondiment.BOTH   -> when (level) {
                GrillLevel.RARE        -> R.drawable.dish_fish_full_rare
                GrillLevel.WELL_DONE   -> R.drawable.dish_fish_full_well_done
                GrillLevel.OVERGRILLED -> R.drawable.dish_fish_full_overgrilled
            }
        }
    }
}

// ── COLUMN COMPONENTS ─────────────────────────────────────────────────

@Composable
private fun LeftColumn(state: GameState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxHeight()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(LightBrown)
                .border(2.dp, WarmOrange.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            state.currentCharacter?.let { character ->
                val customerGif = getCustomerGif(character.name)

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(customerGif)
                        .decoderFactory(if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory() else GifDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = character.name,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp).clip(RoundedCornerShape(12.dp)).background(Brush.verticalGradient(listOf(Color(0xFF4E342E), Color(0xFF3E2723)))).border(2.dp, Gold.copy(alpha = 0.5f), RoundedCornerShape(12.dp)).padding(12.dp)) {
            Column {
                if (state.currentCharacter != null) {
                    Text(
                        "💬 ${state.currentCharacter.name}",
                        color = Gold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Text(
                    state.dialogueWords.joinToString(" "),
                    color = CreamWhite,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
private fun CenterColumn(state: GameState, onSelectMeat: (MeatType) -> Unit, onSelectSide: (SideCondiment) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(1f)) {
            val meats = listOf(MeatType.BEEF to BeefBrown, MeatType.CHICKEN to ChickenOrange, MeatType.PORK to PorkPink, MeatType.FISH to FishBlue)
            meats.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    row.forEach { (meat, _) ->
                        val isSelected = state.selectedMeat == meat
                        val isDisabled = state.meatCooked
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(10.dp)).background(if (isDisabled) DisabledGray.copy(alpha = 0.3f) else Color.Transparent).border(if (isSelected) 3.dp else 0.dp, Gold, RoundedCornerShape(10.dp)).clickable(enabled = !isDisabled) { onSelectMeat(meat) }, contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(id = getMeatSelectDrawable(meat)), contentDescription = meat.displayName, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop, alpha = if (isDisabled) 0.3f else if (isSelected) 1f else 0.7f)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            SideButton(side = SideCondiment.SAUCE,   isSelected = state.selectedSauce,   onClick = { onSelectSide(SideCondiment.SAUCE) },   modifier = Modifier.weight(1f))
            SideButton(side = SideCondiment.POTATO,  isSelected = state.selectedPotato,  onClick = { onSelectSide(SideCondiment.POTATO) }, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SideButton(side: SideCondiment, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(64.dp).clip(RoundedCornerShape(12.dp)).background(if (isSelected) Gold.copy(alpha = 0.2f) else Color.Transparent).border(if (isSelected) 3.dp else 1.dp, if (isSelected) Gold else CreamWhite.copy(alpha = 0.3f), RoundedCornerShape(12.dp)).clickable { onClick() }, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val imageRes = getSideSelectDrawable(side)
            if (imageRes != 0) {
                Image(painter = painterResource(id = imageRes), contentDescription = side.displayName, modifier = Modifier.fillMaxSize(), alpha = if (isSelected) 1f else 0.6f)
            }
        }
    }
}

@Composable
private fun RightColumn(state: GameState, onStartGrill: () -> Unit, onStopGrill: () -> Unit, onSend: () -> Unit, onTrash: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(12.dp)).background(Brush.verticalGradient(listOf(Color(0xFF424242), Color(0xFF212121)))).border(2.dp, Color(0xFF616161), RoundedCornerShape(12.dp)).padding(8.dp)) {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                val canGrill = state.selectedMeat != null && !state.meatCooked
                Box(modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(8.dp)).then(if (canGrill) Modifier.pointerInput(Unit) { detectTapGestures(onPress = { onStartGrill(); tryAwaitRelease(); onStopGrill() }) } else Modifier), contentAlignment = Alignment.Center) {
                    val grillDrawable = if (state.selectedMeat == null) R.drawable.grill else getGrillMeatDrawable(state.selectedMeat, state.isGrilling)
                    GifImage(
                        resourceId = grillDrawable,
                        modifier = Modifier.fillMaxSize()
                    )

                    if (state.selectedMeat != null && state.grilledLevel != null && !state.isGrilling) {
                        Text(state.grilledLevel.displayName, color = when(state.grilledLevel){ GrillLevel.RARE -> RareGreen; GrillLevel.WELL_DONE -> WellDoneAmber; else -> OvergrilledRed }, modifier = Modifier.align(Alignment.BottomCenter).padding(4.dp).background(DarkBrown.copy(alpha = 0.7f), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
                GrillBar(progress = state.grillProgress, isGrilling = state.isGrilling, meatCooked = state.meatCooked, hasMeat = state.selectedMeat != null)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().height(100.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f).fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBrown.copy(alpha = 0.6f))
                    .border(2.dp, WarmOrange.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (state.meatCooked && state.selectedMeat != null && state.grilledLevel != null) {
                    val side = effectiveSide(state.selectedSauce, state.selectedPotato)
                    GifImage(
                        resourceId = getDishDrawable(state.selectedMeat, state.grilledLevel, side),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("DISH\nRESULT", color = CreamWhite.copy(alpha = 0.3f), textAlign = TextAlign.Center, fontSize = 13.sp)
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(onClick = onSend,
                    modifier = Modifier.width(72.dp).weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MoneyGreen, contentColor = DarkBrown),
                    shape = RoundedCornerShape(10.dp))
                { Text("✓", fontSize = 12.sp)}
                Button(onClick = onTrash,
                    modifier = Modifier.width(72.dp).weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = TrashGray, contentColor = CreamWhite),
                    shape = RoundedCornerShape(10.dp))
                { Text("✗", fontSize = 11.sp)}
            }
        }
    }
}

@Composable
private fun GrillBar(progress: Float, isGrilling: Boolean, meatCooked: Boolean, hasMeat: Boolean) {
    val barAlpha = if (hasMeat && !meatCooked) 1f else 0.3f
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth().height(24.dp).clip(RoundedCornerShape(12.dp)).border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(0.15f).fillMaxHeight().background(OvergrilledRed.copy(alpha = barAlpha)))
                Box(modifier = Modifier.weight(0.25f).fillMaxHeight().background(RareGreen.copy(alpha = barAlpha)))
                Box(modifier = Modifier.weight(0.20f).fillMaxHeight().background(OvergrilledRed.copy(alpha = barAlpha)))
                Box(modifier = Modifier.weight(0.25f).fillMaxHeight().background(WellDoneAmber.copy(alpha = barAlpha)))
                Box(modifier = Modifier.weight(0.15f).fillMaxHeight().background(OvergrilledRed.copy(alpha = barAlpha)))
            }
            if (progress > 0f || isGrilling) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxHeight()
                        .width(6.dp)
                        .align(BiasAlignment((progress * 2f) - 1f, 0f))
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color.White)
                        .border(1.dp, DarkBrown, RoundedCornerShape(3.dp)))
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(0.15f)) // Overgrilled zone
            Text(
                text = "Rare",
                modifier = Modifier.weight(0.25f),
                textAlign = TextAlign.Center,
                color = RareGreen,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(0.20f)) // Overgrilled zone
            Text(
                text = "Well Done",
                modifier = Modifier.weight(0.25f),
                textAlign = TextAlign.Center,
                color = WellDoneAmber,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(0.15f)) // Overgrilled zone
        }
    }
}