package com.rago.rickandmortiwiki.data.repositories

import arrow.core.Either
import com.rago.rickandmortiwiki.data.model.CharactersResponse
import com.rago.rickandmortiwiki.data.service.CharactersApiService
import com.rago.rickandmortiwiki.data.utils.network.ErrorResponse
import com.rago.rickandmortiwiki.data.utils.network.SuccessResponse
import com.rago.rickandmortiwiki.data.utils.network.retrofitArrow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersApiService: CharactersApiService
) : CharactersRepository {

    // Se obtienen personajes del servidor
    override fun getCharacters(page: Int): Flow<Either<ErrorResponse, SuccessResponse<CharactersResponse>>> {
        return retrofitArrow(
            charactersApiService.getCharacters(page)
        )
    }
}