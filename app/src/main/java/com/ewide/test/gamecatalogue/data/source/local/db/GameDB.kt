package com.ewide.test.gamecatalogue.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ewide.test.gamecatalogue.data.source.local.model.DetailEntity
import com.ewide.test.gamecatalogue.data.source.remote.model.DealsItem

@Database(entities = [DetailEntity::class], version = 1, exportSchema = false)
abstract class GameDB : RoomDatabase() {

    abstract fun gameDao(): GameDao
}