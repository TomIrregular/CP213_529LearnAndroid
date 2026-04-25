package com.tomweasley.overgrilled.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tomweasley.overgrilled.data.GamePhase
import com.tomweasley.overgrilled.screens.*
import com.tomweasley.overgrilled.viewmodel.GameViewModel

@Composable
fun NavGraph(gameViewModel: GameViewModel = viewModel()) {
    val navController = rememberNavController()
    val state by gameViewModel.state.collectAsState()

    val isMusicEnabled by gameViewModel.isMusicEnabled.collectAsState()

    val playInteract = { gameViewModel.soundManager.playInteract() }

    NavHost(
        navController = navController,
        startDestination = "main_menu",
        enterTransition = { fadeIn(animationSpec = tween(600)) },
        exitTransition = { fadeOut(animationSpec = tween(600)) }
    ) {
        composable("main_menu") {
            LaunchedEffect(Unit) {
                gameViewModel.soundManager.playMainMenuBGM()
            }

            MainMenuScreen(
                highScore = state.highScore,
                isMusicEnabled = isMusicEnabled,
                onStartClick = {
                    playInteract()
                    navController.navigate("how_to_play") { launchSingleTop = true }
                },
                onToggleMusic = { gameViewModel.toggleMusic() }
            )
        }

        composable("how_to_play") {
            HowToPlayScreen(
                onNextClick = {
                    playInteract()
                    gameViewModel.startNewGame()
                    navController.navigate("game") {
                        popUpTo("main_menu") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("game") {
            when (state.gamePhase) {
                GamePhase.PLAYING -> {
                    LaunchedEffect(state.currentDay) {
                        gameViewModel.soundManager.playGameTrackBGM()
                    }

                    GameScreen(
                        state = state,
                        onSelectMeat = { playInteract(); gameViewModel.selectMeat(it) },
                        onStartGrill = { playInteract(); gameViewModel.startGrilling() },
                        onStopGrill = { gameViewModel.stopGrilling() },
                        onSelectSide = { playInteract(); gameViewModel.selectSide(it) },
                        onSend = { playInteract(); gameViewModel.sendDish() },
                        onTrash = { playInteract(); gameViewModel.trashDish() }
                    )
                }

                GamePhase.DAY_SUMMARY -> {
                    LaunchedEffect(Unit) { gameViewModel.soundManager.playResultBGM() }
                    SummaryScreen(
                        currentDay = state.currentDay,
                        dailyEarnings = state.dailyEarnings,
                        dailyQuota = state.dailyQuota,
                        totalMoney = state.totalMoney,
                        onContinue = {
                            playInteract()
                            gameViewModel.continueToNextDay()
                        },
                        onMainMenu = {
                            playInteract()
                            navController.navigate("main_menu") { popUpTo(0) { inclusive = true } }
                        }
                    )
                }

                GamePhase.GAME_OVER -> {
                    LaunchedEffect(Unit) { gameViewModel.soundManager.playResultBGM() }
                    GameOverScreen(
                        currentDay = state.currentDay,
                        dailyEarnings = state.dailyEarnings,
                        dailyQuota = state.dailyQuota,
                        totalMoney = state.totalMoney,
                        onBackToMenu = {
                            playInteract()
                            navController.navigate("main_menu") { popUpTo(0) { inclusive = true } }
                        }
                    )
                }
            }
        }
    }
}