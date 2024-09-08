package com.rago.rickandmortiwiki.data.utils.network

data class SuccessResponse<T>(
    val code: Int = 0,
    val data: T? = null,
)