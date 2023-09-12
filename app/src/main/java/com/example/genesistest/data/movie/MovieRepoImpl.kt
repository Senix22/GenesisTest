package com.example.genesistest.data.movie

import com.example.genesistest.data.api.MovieApi
import com.example.genesistest.data.models.UsualMovieResult
import com.example.genesistest.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val api: MovieApi,
    private val mapper: MovieResponseMapper
) : MovieRepository {
    override suspend fun requestPopularMovie(page: Int?): UsualMovieResult {
       return mapper.mapMovieResponse(api.getMovieList(page = page))
    }
}