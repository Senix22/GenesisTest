package com.example.genesistest.data

import androidx.paging.PagingSource
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.models.MovieResult
import com.example.genesistest.data.movie.OfflineMovieRepository
import com.example.genesistest.domain.repository.MovieRepository
import com.example.genesistest.data.page.MovieSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class MovieSourceTest {



    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var useCase: OfflineMovieRepository

    private lateinit var movieSource: MovieSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieSource = MovieSource(movieRepository, useCase)
    }

    companion object {
        val movie = MovieDetailEntity(
            true,
            "",
            mutableListOf(1, 2, 3),
            1,
            "",
            "",
            "",
            7.0f,
            posterPath = "",
            "",
            "",
            true,
            120f,
            120
        )
    }

    @Test
    fun `loadReturnsSuccessResult`() = runTest {
        // Створюємо тестові дані
        val nextPage = 1
        val movieResult =
            MovieResult.Success(page = 1, pages = 20, listOf<MovieDetailEntity>(movie))

        // Підготовка для імітації результату з movieRepository
        Mockito.`when`(movieRepository.requestPopularMovie(nextPage)).thenReturn(movieResult)

        // Викликаємо метод, який ми хочемо протестувати
        val loadParams = PagingSource.LoadParams.Refresh(key = nextPage, loadSize = 10,placeholdersEnabled = false)
        val loadResult = movieSource.load(loadParams)

        // Перевіряємо, чи отриманий результат є успішним
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        assertEquals(movieResult.result, (loadResult as PagingSource.LoadResult.Page).data)
    }

    @Test
    fun `loadReturnsErrorResult`() = runTest {
        // Підготовка для імітації помилкового результату з movieRepository
        val nextPage = 1
        val errorMessage = "Помилка завантаження даних"
        val movieResult = MovieResult.Failure<MovieDetailEntity>(code = 1, errorText = errorMessage)

        Mockito.`when`(movieRepository.requestPopularMovie(nextPage)).thenReturn(movieResult)

        val loadParams = PagingSource.LoadParams.Refresh(key = nextPage, loadSize = 10,placeholdersEnabled = false)
        val loadResult = movieSource.load(loadParams)

        // Перевіряємо, чи отриманий результат є помилковим
        assertTrue(loadResult is PagingSource.LoadResult.Error)
        assertEquals(errorMessage, (loadResult as PagingSource.LoadResult.Error).throwable.message)
    }

}