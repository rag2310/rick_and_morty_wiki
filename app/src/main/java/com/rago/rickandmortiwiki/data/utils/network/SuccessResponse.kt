package com.rago.rickandmortiwiki.data.utils.network

//Maneja el resultado correcto del servidor.
data class SuccessResponse<T>(
    val code: Int = 0,
    val data: T? = null,
)