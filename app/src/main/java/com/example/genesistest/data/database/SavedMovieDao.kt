package com.example.genesistest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.genesistest.data.database.MovieDatabase.Companion.DATABASE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMovieDao {

    @Query("SELECT * FROM $DATABASE_TABLE")
    fun getAll(): Flow<List<SavedMovieDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SavedMovieDto)

    @Query("DELETE FROM $DATABASE_TABLE WHERE id == :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM $DATABASE_TABLE WHERE id == :id")
    suspend fun get(id: Long): SavedMovieDto?

    @Query("SELECT EXISTS (SELECT 1 FROM $DATABASE_TABLE WHERE id = :id)")
    suspend fun exists(id: Long): Boolean
}