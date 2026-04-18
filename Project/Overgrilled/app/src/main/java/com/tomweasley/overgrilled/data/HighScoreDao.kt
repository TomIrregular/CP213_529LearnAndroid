package com.tomweasley.overgrilled.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_score WHERE id = 1")
    suspend fun getHighScore(): HighScoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: HighScoreEntity)
}
