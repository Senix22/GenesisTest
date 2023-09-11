package com.example.genesistest.data.movie

import android.util.Log
import com.example.genesistest.data.database.SavedMovieDao
import com.example.genesistest.data.database.MovieDatabase
import com.example.genesistest.data.database.SavedMovieDataMapper
import com.example.genesistest.data.models.MovieDetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedMovieRepository @Inject constructor(
    private val mapper: SavedMovieDataMapper,
    private val database: MovieDatabase,
) {
    private val dao: SavedMovieDao
        get() = database.savedDao()

    val allMovie: Flow<List<MovieDetailEntity>>
        get() = dao.getAll()
            .map { news -> news.map { mapper(it) } }

    suspend fun add(entity: MovieDetailEntity) =
        dao.insert(
            mapper(entity)
        ).let {
            Log.d("dao", "add")
        }

    suspend fun isSaved(entity: MovieDetailEntity): Boolean =
        dao.get(entity.id) != null

    suspend fun remove(entity: MovieDetailEntity) =
        dao.delete(entity.id).let {
            Log.d("dao", "remove")
        }

    fun collectIsSaved(entity: MovieDetailEntity): Flow<Boolean> =
        allMovie.map { dao.exists(entity.id) }
}