package com.example.genesistest.data.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.genesistest.data.movie.MovieRepository
import com.example.genesistest.data.models.MovieDetailEntity

import com.example.genesistest.data.models.MovieResult
import com.example.genesistest.domain.GroupUseCase
import javax.inject.Inject

class MovieSource @Inject constructor(
    private val movieRepository: MovieRepository,
    private val useCase: GroupUseCase
) : PagingSource<Int, MovieDetailEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDetailEntity>): Int {
        return INITIAL_REFRESH_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetailEntity> {
        return try {
            val nextPage = params.key ?: INITIAL_REFRESH_KEY
            val movieResult = movieRepository.requestPopularMovie(nextPage)

            if (movieResult is MovieResult.Success) {
                if (params is LoadParams.Refresh) {
                    useCase.cacheData(movieResult.result.orEmpty())
                }

                LoadResult.Page(
                    data = movieResult.result ?: emptyList(),
                    prevKey = if (nextPage == INITIAL_REFRESH_KEY) null else nextPage - NEXT_PAGE,
                    nextKey = nextPage.plus(NEXT_PAGE),
                )
            } else {

                LoadResult.Error(IllegalStateException((movieResult as MovieResult.Failure).errorText))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    private companion object {
        const val INITIAL_REFRESH_KEY = 1
        const val NEXT_PAGE = 1
    }


}