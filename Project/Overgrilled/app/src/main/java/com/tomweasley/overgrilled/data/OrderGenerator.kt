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

    private val characters = listOf(sc, hm)

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

            else -> "I want a $grillText ${order.meat.displayName.lowercase()} steak $sideText."
        }
    }
}
