package com.rago.rickandmortiwiki.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rago.rickandmortiwiki.data.model.CharactersResponse
import com.rago.rickandmortiwiki.data.usecases.CharactersUseCase
import com.rago.rickandmortiwiki.data.utils.network.ErrorResponse
import com.rago.rickandmortiwiki.data.utils.network.SuccessResponse
import com.rago.rickandmortiwiki.presentation.uistate.CharactersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharactersUIState> = MutableStateFlow(
        CharactersUIState(
            getCharacters = ::getCharacters,
            onClearError = ::onClearError
        )
    )
    val uiState: StateFlow<CharactersUIState> = _uiState.asStateFlow()

    private fun onClearError() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(error = "", errorRes = -1)
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }
            val currentPage = _uiState.value.currentPage
            charactersUseCase.executeGetCharacters(currentPage).collect { response ->

                response.fold({ error: ErrorResponse ->
                    _uiState.update {
                        it.copy(
                            loading = false,
                            error = error.error ?: "",
                            errorRes = error.errorRes ?: -1
                        )
                    }
                }, { success: SuccessResponse<CharactersResponse> ->
                    val data = success.data
                    val currentCharacters = _uiState.value.characters.toMutableList()
                    if (data != null) {
                        val result = data.results ?: listOf()
                        currentCharacters.addAll(result)
                        _uiState.update {
                            it.copy(
                                loading = false,
                                characters = currentCharacters,
                                currentPage = currentPage + 1
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(loading = false)
                        }
                    }
                })
            }
        }
    }
}