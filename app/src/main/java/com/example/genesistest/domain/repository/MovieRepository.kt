package com.example.genesistest.domain.repository


import com.example.genesistest.data.models.UsualMovieResult


interface MovieRepository{
    suspend fun requestPopularMovie(page : Int?) : UsualMovieResult
}