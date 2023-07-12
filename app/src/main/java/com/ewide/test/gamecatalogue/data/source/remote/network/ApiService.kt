package com.ewide.test.gamecatalogue.data.source.remote.network

import com.ewide.test.gamecatalogue.data.source.remote.model.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getListGames(
        @Query("title") title: String
    ): List<GamesResponse>
}