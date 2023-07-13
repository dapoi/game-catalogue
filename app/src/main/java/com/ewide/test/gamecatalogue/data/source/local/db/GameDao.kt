package com.ewide.test.gamecatalogue.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ewide.test.gamecatalogue.data.source.local.model.DetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: DetailEntity)

    @Delete
    suspend fun deleteGame(game: DetailEntity)

    @Query("SELECT * FROM game")
    fun getFavoriteGame(): Flow<List<DetailEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM game WHERE gameID = :gameID)")
    fun isFavorite(gameID: String): Flow<Boolean>
}