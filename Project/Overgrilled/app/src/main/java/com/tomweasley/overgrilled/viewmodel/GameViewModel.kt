package com.tomweasley.overgrilled.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomweasley.overgrilled.data.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.tomweasley.overgrilled.viewmodel.SoundManager
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val highScoreDao = db.highScoreDao()

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var timerJob: Job? = null
    private var dialogueJob: Job? = null
    private var grillJob: Job? = null

    init {
        loadHighScore()
    }

    // ── Sound manager ──────────────────────────────────────────────────────
    val soundManager = SoundManager(application)

    private val _isMusicEnabled = MutableStateFlow(true)
    val isMusicEnabled = _isMusicEnabled.asStateFlow()

    private val _isSfxEnabled = MutableStateFlow(true)
    val isSfxEnabled = _isSfxEnabled.asStateFlow()

    fun toggleMusic() {
        val newState = !_isMusicEnabled.value
        _isMusicEnabled.value = newState
        soundManager.isMusicEnabled = newState
        soundManager.playInteract()
    }

    fun toggleSfx() {
        val newState = !_isSfxEnabled.value
        _isSfxEnabled.value = newState
        soundManager.isSfxEnabled = newState
        if (newState) soundManager.playInteract()
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }

    // ── High score ──────────────────────────────────────────────────────

    private fun loadHighScore() {
        viewModelScope.launch {
            val entity = highScoreDao.getHighScore()
            _state.update { it.copy(highScore = entity?.score ?: 0) }
        }
    }

    private fun saveHighScoreIfNeeded(totalMoney: Int) {
        viewModelScope.launch {
            val current = highScoreDao.getHighScore()?.score ?: 0
            if (totalMoney > current) {
                highScoreDao.insertOrUpdate(HighScoreEntity(score = totalMoney))
                _state.update { it.copy(highScore = totalMoney) }
            }
        }
    }

    // ── Game lifecycle ──────────────────────────────────────────────────

    fun startNewGame() {
        timerJob?.cancel()
        dialogueJob?.cancel()
        grillJob?.cancel()
        _state.value = GameState(highScore = _state.value.highScore)
        startDay()
    }

    private fun startDay() {
        spawnNewCustomer()
        startTimer()
    }

    fun continueToNextDay() {
        val s = _state.value
        _state.update {
            it.copy(
                currentDay = s.currentDay + 1,
                dailyQuota = s.dailyQuota + 30,
                dailyEarnings = 0,
                timeRemainingMs = 180_000L,
                gamePhase = GamePhase.PLAYING,
                selectedMeat = null,
                grillProgress = 0f,
                isGrilling = false,
                grilledLevel = null,
                meatCooked = false,
                selectedSauce = false,
                selectedPotato = false,
                lastEarned = 0
            )
        }
        startDay()
    }

    // ── Timer ───────────────────────────────────────────────────────────

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.timeRemainingMs > 0) {
                delay(100L)
                _state.update { it.copy(timeRemainingMs = it.timeRemainingMs - 100L) }
            }
            endDay()
        }
    }

    private fun endDay() {
        timerJob?.cancel()
        dialogueJob?.cancel()
        grillJob?.cancel()

        val s = _state.value
        val newTotal = s.totalMoney + s.dailyEarnings

        if (s.dailyEarnings >= s.dailyQuota) {
            _state.update {
                it.copy(
                    gamePhase = GamePhase.DAY_SUMMARY,
                    totalMoney = newTotal
                )
            }
        } else {
            _state.update {
                it.copy(
                    gamePhase = GamePhase.GAME_OVER,
                    totalMoney = newTotal
                )
            }
            saveHighScoreIfNeeded(newTotal)
        }
    }

    // ── Customer ────────────────────────────────────────────────────────

    private fun spawnNewCustomer() {
        dialogueJob?.cancel()
        val (character, order) = OrderGenerator.generateOrder(_state.value.currentDay)

        val dialogue = OrderGenerator.generateDialogue(character, order)
        val words = dialogue.split(" ")

        _state.update {
            it.copy(
                currentCharacter = character,
                currentOrder = order,
                dialogueWords = emptyList(),
                totalDialogueWords = words,
                selectedMeat = null,
                grillProgress = 0f,
                isGrilling = false,
                grilledLevel = null,
                meatCooked = false,
                selectedSauce = false,
                selectedPotato = false,
                lastEarned = 0
            )
        }

        dialogueJob = viewModelScope.launch {
            for (i in words.indices) {
                delay(200L)
                _state.update {
                    it.copy(dialogueWords = words.subList(0, i + 1))
                }
            }
        }
    }

    // ── Meat selection ──────────────────────────────────────────────────

    fun selectMeat(type: MeatType) {
        if (_state.value.meatCooked) return
        _state.update { it.copy(selectedMeat = type, grillProgress = 0f, grilledLevel = null) }
    }

    // ── Grill mechanic ─────────────────────────────────────────────────

    fun startGrilling() {
        val s = _state.value
        if (s.meatCooked || s.selectedMeat == null) return

        _state.update { it.copy(isGrilling = true, grillProgress = 0f) }

        grillJob?.cancel()
        grillJob = viewModelScope.launch {
            while (_state.value.isGrilling && _state.value.grillProgress < 1f) {
                delay(32L)
                _state.update {
                    it.copy(grillProgress = (it.grillProgress + 0.007f).coerceAtMost(1f))
                }
            }
            if (_state.value.grillProgress >= 1f) {
                stopGrilling()
            }
        }
    }

    fun stopGrilling() {
        if (!_state.value.isGrilling && !_state.value.meatCooked) return
        grillJob?.cancel()

        val progress = _state.value.grillProgress
        val level = when {
            progress in 0.15f..0.40f -> GrillLevel.RARE
            progress in 0.60f..0.85f -> GrillLevel.WELL_DONE
            else -> GrillLevel.OVERGRILLED // Everything else is burnt!
        }

        _state.update {
            it.copy(
                isGrilling = false,
                grilledLevel = level,
                meatCooked = true
            )
        }
    }

    // ── Side condiments ─────────────────────────────────────────────────

    fun selectSide(side: SideCondiment) {
        _state.update {
            when (side) {
                SideCondiment.SAUCE  -> it.copy(selectedSauce  = !it.selectedSauce)
                SideCondiment.POTATO -> it.copy(selectedPotato = !it.selectedPotato)
                else -> it
            }
        }
    }

    // ── Send / Trash ────────────────────────────────────────────────────

    fun sendDish() {
        val s = _state.value
        val order = s.currentOrder ?: return

        val playerSide = effectiveSide(s.selectedSauce, s.selectedPotato)

        var correct = 0
        if (s.selectedMeat == order.meat) correct++
        if (s.grilledLevel == order.grillLevel) correct++
        if (playerSide == order.side) correct++

        val earned = 20 * correct

        _state.update {
            it.copy(
                dailyEarnings = it.dailyEarnings + earned,
                lastEarned = earned
            )
        }

        spawnNewCustomer()
    }

    fun trashDish() {
        _state.update {
            it.copy(
                selectedMeat = null,
                grillProgress = 0f,
                isGrilling = false,
                grilledLevel = null,
                meatCooked = false,
                selectedSauce = false,
                selectedPotato = false,
                lastEarned = 0
            )
        }
    }
}
