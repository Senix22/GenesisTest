package com.example.genesistest.domain

import com.example.genesistest.data.database.MovieDatabase
import com.example.genesistest.data.database.caching.OfflineMovieMapper
import com.example.genesistest.data.models.MovieDetailEntity
import javax.inject.Inject

class GroupUseCase @Inject constructor(
    private val db: MovieDatabase,
    val mapper: OfflineMovieMapper
){
    suspend fun cacheData(list: List<MovieDetailEntity>) = db.offlineMoviesDao().insertAndDeleteInTransaction(
        list.map { mapper(it) }
    )
}