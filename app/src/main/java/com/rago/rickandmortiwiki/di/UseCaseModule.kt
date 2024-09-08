package com.rago.rickandmortiwiki.di

import com.rago.rickandmortiwiki.data.repositories.CharactersRepository
import com.rago.rickandmortiwiki.data.usecases.CharactersUseCase
import com.rago.rickandmortiwiki.data.usecases.CharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providerCharactersUseCase(
        charactersRepository: CharactersRepository
    ): CharactersUseCase = CharactersUseCaseImpl(
        charactersRepository
    )
}