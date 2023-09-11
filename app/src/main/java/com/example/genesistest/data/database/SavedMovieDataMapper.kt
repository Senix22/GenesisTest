package com.example.genesistest.data.database

import com.example.genesistest.data.models.MovieDetailEntity
import javax.inject.Inject

class SavedMovieDataMapper @Inject constructor() {

    operator fun invoke(dto: SavedMovieDto): MovieDetailEntity =
        with(dto) {
            MovieDetailEntity(
                adult = adult,
                backdropPath = backdropPath,
                genreIds = mutableListOf(),
                id = id,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount
            )
        }

    operator fun invoke(entity: MovieDetailEntity): SavedMovieDto =
        with(entity) {
            SavedMovieDto(
                adult = adult,
                backdropPath = backdropPath,
                id = id,
                originalLanguage = originalLanguage,
                originalTitle = originalTitle,
                overview = overview,
                popularity = popularity,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                video = video,
                voteAverage = voteAverage,
                voteCount = voteCount
            )
        }
}
