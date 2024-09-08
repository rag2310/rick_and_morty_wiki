package com.rago.rickandmortiwiki.presentation.uistate

import androidx.annotation.StringRes
import com.rago.rickandmortiwiki.data.model.Result


data class CharactersUIState(
    val loading: Boolean = false,
    val characters: List<Result> = listOf(),
    val getCharacters: () -> Unit = {},
    val currentPage: Int = 1,
    val totalPage: Int = 0,
    val error: String = "",
    @StringRes val errorRes: Int = -1,
    val onClearError: () -> Unit = {},
    val currentIndex: Int = 0,
)