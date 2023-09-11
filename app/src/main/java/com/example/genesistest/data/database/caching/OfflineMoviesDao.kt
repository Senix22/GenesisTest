package com.example.genesistest.data.database.caching

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.genesistest.data.database.MovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDto>)

    @Query("Select * From movies")
    fun getAllMovies(): Flow<List<MovieDto>>

    @Delete
    suspend fun delete(song: List<MovieDto>)

    @Query("Delete From movies")
    suspend fun clearAllMovies()

    @Transaction
    suspend fun insertAndDeleteInTransaction(newSong: List<MovieDto>) {
        clearAllMovies()
        insertAll(newSong)
    }
}