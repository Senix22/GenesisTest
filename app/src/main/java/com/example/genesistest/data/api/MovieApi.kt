package com.example.genesistest.data.api

import com.example.genesistest.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query


const val KEY = "e64171211a64c2252b5e7368c3fdb57e"

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String? = KEY,
        @Query("page") page: Int?,
        @Query("sort_by") sortBy: String = "primary_release_date.desc",
        @Query("vote_average.gte") voteAverageGte: Float = 7.0f,
        @Query("vote_average.lte") voteAverageLte: Float = 10.0f,
        @Query("vote_count.gte") voteCountGte: Float? = 100f,
    ): NetworkResponse<MovieResponse, Any>

}