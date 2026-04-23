package com.tomweasley.overgrilled.data

import kotlin.random.Random

object OrderGenerator {

    private val sc = Character(
        name = "Science",
        orderPool = listOf(
            Order(MeatType.BEEF, GrillLevel.WELL_DONE, SideCondiment.NONE),
            Order(MeatType.CHICKEN, GrillLevel.WELL_DONE, SideCondiment.NONE),
            Order(MeatType.PORK, GrillLevel.WELL_DONE, SideCondiment.NONE),
            Order(MeatType.FISH, GrillLevel.WELL_DONE, SideCondiment.NONE)
        )
    )

    private val hm = Character(
        name = "Humanity",
        orderPool = listOf(
            Order(MeatType.PORK, GrillLevel.RARE, SideCondiment.SAUCE),
            Order(MeatType.CHICKEN, GrillLevel.WELL_DONE, SideCondiment.POTATO)
        )
    )

    private val fofa = Character(
        name = "Fofa",
        orderPool = listOf(
            Order(MeatType.BEEF, GrillLevel.RARE, SideCondiment.NONE),
            Order(MeatType.CHICKEN, GrillLevel.RARE, SideCondiment.NONE),
            Order(MeatType.PORK, GrillLevel.RARE, SideCondiment.NONE),
            Order(MeatType.FISH, GrillLevel.RARE, SideCondiment.NONE)
        )
    )

    /** Returns a random (Character, Order) pair based on the current day. */
    fun generateOrder(currentDay: Int): Pair<Character, Order> {
        val availableCharacters = when {
            currentDay == 1 -> listOf(sc)
            currentDay == 2 -> listOf(sc, hm)
            else -> listOf(sc, hm, fofa)
        }

        val character = availableCharacters.random()
        val order = character.orderPool.random()
        return character to order
    }

    /** Builds the dialogue string the customer will say, word-by-word. */
    fun generateDialogue(character: Character, order: Order): String {
        val grillText = when (order.grillLevel) {
            GrillLevel.RARE -> "rare"
            GrillLevel.WELL_DONE -> "well done"
            GrillLevel.OVERGRILLED -> "overgrilled"
        }
        val sideText = when (order.side) {
            SideCondiment.SAUCE  -> "with sauce"
            SideCondiment.POTATO -> "with potato"
            SideCondiment.BOTH   -> "with sauce and potato"
            SideCondiment.NONE   -> "with nothing on the side"
        }

        return when (character.name) {
            "Science" -> listOf(
                "One $grillText ${order.meat.displayName.lowercase()} to go, please!",
                "Could I get $grillText ${order.meat.displayName.lowercase()}? Thank you.",
                "$grillText ${order.meat.displayName.lowercase()} $sideText... Please!"
            ).random()

            "Humanity" -> listOf(
                "Hey! I'll have my usual please. You know, $grillText ${order.meat.displayName.lowercase()} $sideText.",
                "Sooo many choices to choose from! ${order.meat.displayName} looks good. I'd like that $grillText $sideText!",
                "Hey-o! Good business today? $grillText ${order.meat.displayName.lowercase()}, please. Oh, and $sideText. Can't forget that!"
            ).random()

            "Fofa" -> listOf(
                "H-hi! Uh, a Well-done ${order.meat.displayName.lowercase()}, please. No, wait! A $grillText ${order.meat.displayName.lowercase()}. Sorry!",
                "Uh... C-could I have a ${order.meat.displayName.lowercase()}. $grillText. Nothing else. Thank you!",
                "Hi! I'll have uh... a rare chicken- No, wait! A $grillText ${order.meat.displayName.lowercase()}... Sorry!"
            ).random()

            else -> "I want a $grillText ${order.meat.displayName.lowercase()} steak $sideText."
        }
    }
}