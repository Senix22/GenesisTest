package com.example.genesistest.data.movie

import com.example.genesistest.data.database.MovieDatabase

import com.example.genesistest.data.database.caching.OfflineMovieMapper
import com.example.genesistest.data.database.caching.OfflineMoviesDao
import com.example.genesistest.data.models.MovieDetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineMovieRepository @Inject constructor(
    private val mapper: OfflineMovieMapper,
    private val database: MovieDatabase,
) {
    private val dao: OfflineMoviesDao
        get() = database.offlineMoviesDao()

    val allOfflineMovie: Flow<List<MovieDetailEntity>>
        get() = dao.getAllMovies()
            .map { news -> news.map { mapper(it) } }

}