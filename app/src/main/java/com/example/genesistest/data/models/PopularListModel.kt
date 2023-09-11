package com.example.genesistest.data.models


typealias UsualMovieResult = MovieResult<MovieDetailEntity>

sealed class MovieResult<out T> {
    data class Success<T>(
        val page: Int,
        val pages: Int,
        val result: List<T>?
    ) : MovieResult<T>()

    data class Failure<T>(
        val errorText: String,
        val code: Int?
    ) : MovieResult<T>()
}


data class MovieDetailEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds : MutableList<Int>,
    val id: Long,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
)

data class MovieWithSavedStateEntity(
    val isSaved: Boolean,
    val entity: MovieDetailEntity
)
