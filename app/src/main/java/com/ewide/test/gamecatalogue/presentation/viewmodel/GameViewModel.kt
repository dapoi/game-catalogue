package com.ewide.test.gamecatalogue.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewide.test.gamecatalogue.data.repository.GameRepository
import com.ewide.test.gamecatalogue.data.source.remote.model.GamesResponse
import com.ewide.test.gamecatalogue.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private var _getListGame = MutableStateFlow<Resource<List<GamesResponse>>>(Resource.Loading)
    val getListGame: StateFlow<Resource<List<GamesResponse>>> get() = _getListGame

    fun setListGame(title: String) {
        viewModelScope.launch {
            gameRepository.getListGames(title).collect {
                _getListGame.value = it
            }
        }
    }
}