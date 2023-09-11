package com.example.genesistest.data.movie

import com.example.genesistest.data.api.MovieApi
import com.example.genesistest.data.models.UsualMovieResult
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val mapper: MovieResponseMapper
) {
    suspend fun requestPopularMovie(page: Int? = null): UsualMovieResult =
        mapper.mapMovieResponse(api.getMovieList(page = page))
}