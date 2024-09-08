package com.rago.rickandmortiwiki.di

import com.rago.rickandmortiwiki.data.repositories.CharactersRepository
import com.rago.rickandmortiwiki.data.repositories.CharactersRepositoryImpl
import com.rago.rickandmortiwiki.data.service.CharactersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providerCharactersRepository(
        charactersApiService: CharactersApiService
    ): CharactersRepository {
        return CharactersRepositoryImpl(
            charactersApiService
        )
    }
}