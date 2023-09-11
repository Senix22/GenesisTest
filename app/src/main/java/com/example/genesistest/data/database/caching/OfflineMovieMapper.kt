package com.example.genesistest.data.database.caching

import com.example.genesistest.data.database.MovieDto
import com.example.genesistest.data.models.MovieDetailEntity
import javax.inject.Inject

class OfflineMovieMapper @Inject constructor() {

    operator fun invoke(dto: MovieDto): MovieDetailEntity =
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

    operator fun invoke(entity: MovieDetailEntity): MovieDto =
        with(entity) {
            MovieDto(
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
