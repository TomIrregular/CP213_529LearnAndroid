package com.tomweasley.overgrilled.data

import kotlin.random.Random

object OrderGenerator {

    private val student = Character(
        name = "Student",
        orderPool = listOf(
            Order(MeatType.BEEF, GrillLevel.WELL_DONE, SideCondiment.POTATO),
            Order(MeatType.CHICKEN, GrillLevel.WELL_DONE, SideCondiment.NONE)
        )
    )

    private val teacher = Character(
        name = "Teacher",
        orderPool = listOf(
            Order(MeatType.FISH, GrillLevel.RARE, SideCondiment.SAUCE),
            Order(MeatType.PORK, GrillLevel.RARE, SideCondiment.SAUCE),
            Order(MeatType.BEEF, GrillLevel.WELL_DONE, SideCondiment.BOTH),
            Order(MeatType.CHICKEN, GrillLevel.WELL_DONE, SideCondiment.BOTH)
        )
    )

    private val characters = listOf(student, teacher)

    /** Returns a random (Character, Order) pair. */
    fun generateOrder(): Pair<Character, Order> {
        val character = characters[Random.nextInt(characters.size)]
        val order = character.orderPool[Random.nextInt(character.orderPool.size)]
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
            "Student" -> listOf(
                "Hey! I'd like a $grillText ${order.meat.displayName.lowercase()} steak $sideText please!",
                "Hi there! Can I get a $grillText ${order.meat.displayName.lowercase()} $sideText?",
                "Yo! One $grillText ${order.meat.displayName.lowercase()} steak $sideText thanks!"
            ).random()

            "Teacher" -> listOf(
                "Good day. I'll have a $grillText ${order.meat.displayName.lowercase()} steak $sideText.",
                "Hello. One $grillText ${order.meat.displayName.lowercase()} $sideText if you please.",
                "Excuse me. I'd like a $grillText ${order.meat.displayName.lowercase()} steak $sideText."
            ).random()

            else -> "I want a $grillText ${order.meat.displayName.lowercase()} steak $sideText."
        }
    }
}
