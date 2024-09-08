package com.rago.rickandmortiwiki.data.utils.network

import androidx.annotation.StringRes


//Maneja la respuesta cuando es error.
data class ErrorResponse(
    val code: Int = 0,
    val error: String? = null,
    @StringRes val errorRes: Int? = -1,
)