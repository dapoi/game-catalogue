package com.ewide.test.gamecatalogue.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewide.test.gamecatalogue.data.repository.GameRepository
import com.ewide.test.gamecatalogue.data.source.remote.model.DetailResponse
import com.ewide.test.gamecatalogue.data.source.remote.model.GamesResponse
import com.ewide.test.gamecatalogue.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _getListGame = MutableStateFlow<Resource<List<GamesResponse>>>(Resource.Loading)
    val getListGame: StateFlow<Resource<List<GamesResponse>>> get() = _getListGame

    private var _getDetail = MutableStateFlow<Resource<DetailResponse>>(Resource.Loading)
    val getDetail: StateFlow<Resource<DetailResponse>> get() = _getDetail

    fun setListGame(title: String) {
        viewModelScope.launch {
            gameRepository.getListGames(title).collect {
                _getListGame.value = it
            }
        }
    }

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>("gameID")?.let { id ->
                gameRepository.getDetail(id).collect {
                    _getDetail.value = it
                }
            }
        }
    }
}