package com.tomweasley.overgrilled.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_score")
data class HighScoreEntity(
    @PrimaryKey val id: Int = 1,
    val score: Int = 0
)
