package com.rago.rickandmortiwiki.data.repositories

import arrow.core.Either
import com.rago.rickandmortiwiki.data.model.CharactersResponse
import com.rago.rickandmortiwiki.data.utils.network.ErrorResponse
import com.rago.rickandmortiwiki.data.utils.network.SuccessResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharacters(page: Int): Flow<Either<ErrorResponse, SuccessResponse<CharactersResponse>>>
}