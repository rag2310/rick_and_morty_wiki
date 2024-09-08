package com.rago.rickandmortiwiki.data.usecases

import arrow.core.Either
import com.rago.rickandmortiwiki.data.model.CharactersResponse
import com.rago.rickandmortiwiki.data.repositories.CharactersRepository
import com.rago.rickandmortiwiki.data.utils.network.ErrorResponse
import com.rago.rickandmortiwiki.data.utils.network.SuccessResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : CharactersUseCase {
    override fun executeGetCharacters(page: Int): Flow<Either<ErrorResponse, SuccessResponse<CharactersResponse>>> {
        return charactersRepository.getCharacters(page)
    }
}