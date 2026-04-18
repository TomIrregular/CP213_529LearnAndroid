package com.tomweasley.overgrilled.data

enum class MeatType(val displayName: String) {
    BEEF("Beef"),
    CHICKEN("Chicken"),
    PORK("Pork"),
    FISH("Fish")
}

enum class GrillLevel(val displayName: String) {
    RARE("Rare"),
    WELL_DONE("Well Done"),
    OVERGRILLED("Overgrilled")
}

enum class SideCondiment(val displayName: String) {
    SAUCE("Sauce"),
    POTATO("Potato"),
    NONE("No Side")
}

data class Order(
    val meat: MeatType,
    val grillLevel: GrillLevel,
    val side: SideCondiment
)

data class Character(
    val name: String,
    val orderPool: List<Order>
)

enum class GamePhase {
    PLAYING,
    DAY_SUMMARY,
    GAME_OVER
}

data class GameState(
    // Day / Timer
    val currentDay: Int = 1,
    val dailyQuota: Int = 60,
    val dailyEarnings: Int = 0,
    val totalMoney: Int = 0,
    val timeRemainingMs: Long = 180_000L,

    // Current customer
    val currentCharacter: Character? = null,
    val currentOrder: Order? = null,
    val dialogueWords: List<String> = emptyList(),
    val totalDialogueWords: List<String> = emptyList(),

    // Dish state
    val selectedMeat: MeatType? = null,
    val grillProgress: Float = 0f,
    val isGrilling: Boolean = false,
    val grilledLevel: GrillLevel? = null,
    val meatCooked: Boolean = false,
    val selectedSide: SideCondiment = SideCondiment.NONE,

    // Phase
    val gamePhase: GamePhase = GamePhase.PLAYING,

    // Score
    val highScore: Int = 0,
    val lastEarned: Int = 0
)
