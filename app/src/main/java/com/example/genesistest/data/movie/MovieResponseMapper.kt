package com.example.genesistest.data.movie

import com.example.genesistest.common.asMovie
import com.example.genesistest.data.api.MovieResponse
import com.example.genesistest.data.models.MovieResult
import com.example.genesistest.data.models.UsualMovieResult
import com.example.genesistest.network.NetworkResponse
import javax.inject.Inject

class MovieResponseMapper @Inject constructor() {

    fun mapMovieResponse(resultResponse: NetworkResponse<MovieResponse, Any>): UsualMovieResult {
        return when (resultResponse) {
            is NetworkResponse.Success -> {
                with(resultResponse.body) {
                    MovieResult.Success(
                        page = page,
                        pages = pages,
                        result = movies?.map {
                            it.asMovie()
                        }

                    )
                }
            }

            is NetworkResponse.NetworkError -> MovieResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            )

            is NetworkResponse.ApiError -> (
                    MovieResult.Failure(
                        resultResponse.body.toString(),
                        resultResponse.code
                    )
                    )

            is NetworkResponse.UnknownError -> (MovieResult.Failure(
                resultResponse.error.message.orEmpty(),
                null
            ))
        }
    }

}