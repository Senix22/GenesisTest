package com.example.genesistest.di

import com.example.genesistest.domain.repository.MovieRepository
import com.example.genesistest.data.movie.MovieRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataProvidersModule {

    @Binds
    abstract fun movieRepository(impl: MovieRepoImpl): MovieRepository

}