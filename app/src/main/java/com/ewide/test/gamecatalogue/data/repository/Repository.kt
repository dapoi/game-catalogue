package com.ewide.test.gamecatalogue.data.repository

import com.ewide.test.gamecatalogue.data.source.remote.model.DetailResponse
import com.ewide.test.gamecatalogue.data.source.remote.model.GamesResponse
import com.ewide.test.gamecatalogue.data.source.remote.network.ApiService
import com.ewide.test.gamecatalogue.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GameRepository {

    override suspend fun getListGames(title: String): Flow<Resource<List<GamesResponse>>> {
        return flow {
            try {
                emit(Resource.Loading)

                val response = apiService.getListGames(title)
                if (response.isNotEmpty()) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error(Throwable("Data is empty")))
                }
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetail(gameID: String): Flow<Resource<DetailResponse>> {
        return flow {
            try {
                emit(Resource.Loading)

                val response = apiService.getDetail(gameID)
                emit(Resource.Success(response))
            } catch (e: Throwable) {
                emit(Resource.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}

interface GameRepository {
    suspend fun getListGames(title: String): Flow<Resource<List<GamesResponse>>>

    suspend fun getDetail(gameID: String): Flow<Resource<DetailResponse>>
}