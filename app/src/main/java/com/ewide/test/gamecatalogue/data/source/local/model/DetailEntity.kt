package com.ewide.test.gamecatalogue.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class DetailEntity(
    @PrimaryKey
    val gameID: String,
    val image: String,
    val gameName: String,
    val price: String,
)
