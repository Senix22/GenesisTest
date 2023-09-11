package com.example.genesistest.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.genesistest.data.database.MovieDatabase
import com.example.genesistest.data.database.caching.OfflineMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InternalModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context =
        application.applicationContext

    @Provides
    @Singleton
    fun provideSavedNewsDatabase(context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAMESPACE
        ).build()

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MovieDatabase): OfflineMoviesDao = moviesDatabase.offlineMoviesDao()

}
