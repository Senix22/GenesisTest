package com.example.genesistest.data.database

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.example.genesistest.data.database.MovieDatabase.Companion.DATABASE_TABLE
import com.example.genesistest.data.database.MovieDatabase.Companion.DATABASE_VERSION
import com.example.genesistest.data.database.caching.OfflineMoviesDao

@Database(
    entities = [SavedMovieDto::class, MovieDto::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun savedDao(): SavedMovieDao
    abstract fun offlineMoviesDao(): OfflineMoviesDao

    companion object {
        const val DATABASE_NAMESPACE = "savedMovieDatabase"
        const val DATABASE_TABLE = "savedMovie"
        const val DATABASE_VERSION = 1
    }
}

@Entity(tableName = DATABASE_TABLE)
data class SavedMovieDto(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String?,
    @ColumnInfo(name = "original_title") val originalTitle: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: Float?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "video") val video: Boolean?,
    @ColumnInfo(name = "vote_average") val voteAverage: Float?,
    @ColumnInfo(name = "vote_count") val voteCount: Int?,
)


@Entity(tableName = "movies")
data class MovieDto(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String?,
    @ColumnInfo(name = "original_title") val originalTitle: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: Float?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "video") val video: Boolean?,
    @ColumnInfo(name = "vote_average") val voteAverage: Float?,
    @ColumnInfo(name = "vote_count") val voteCount: Int?,
)
