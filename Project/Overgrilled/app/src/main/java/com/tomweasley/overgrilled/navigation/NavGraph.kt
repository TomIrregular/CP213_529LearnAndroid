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

    NavHost(
        navController = navController,
        startDestination = "main_menu",
        enterTransition = { fadeIn(animationSpec = tween(600)) },
        exitTransition = { fadeOut(animationSpec = tween(600)) }
    ) {
        composable("main_menu") {
            MainMenuScreen(
                highScore = state.highScore,
                onStartClick = {
                    navController.navigate("how_to_play") {
                        launchSingleTop = true
                    }
                },
                onOptionClick = { /* TODO: options */ }
            )
        }

        composable("how_to_play") {
            HowToPlayScreen(
                onNextClick = {
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
                    GameScreen(
                        state = state,
                        onSelectMeat = gameViewModel::selectMeat,
                        onStartGrill = gameViewModel::startGrilling,
                        onStopGrill = gameViewModel::stopGrilling,
                        onSelectSide = gameViewModel::selectSide,
                        onSend = gameViewModel::sendDish,
                        onTrash = gameViewModel::trashDish
                    )
                }

                GamePhase.DAY_SUMMARY -> {
                    SummaryScreen(
                        currentDay = state.currentDay,
                        dailyEarnings = state.dailyEarnings,
                        dailyQuota = state.dailyQuota,
                        totalMoney = state.totalMoney,
                        onContinue = gameViewModel::continueToNextDay
                    )
                }

                GamePhase.GAME_OVER -> {
                    GameOverScreen(
                        currentDay = state.currentDay,
                        dailyEarnings = state.dailyEarnings,
                        dailyQuota = state.dailyQuota,
                        totalMoney = state.totalMoney,
                        highScore = state.highScore,
                        onBackToMenu = {
                            navController.navigate("main_menu") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
