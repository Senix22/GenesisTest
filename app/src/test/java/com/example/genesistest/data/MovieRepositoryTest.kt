package com.example.genesistest.data

import com.example.genesistest.data.api.MovieApi
import com.example.genesistest.data.api.MovieResponse
import com.example.genesistest.data.api.ResultResponse
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.models.MovieResult
import com.example.genesistest.data.models.UsualMovieResult
import com.example.genesistest.data.movie.MovieRepository
import com.example.genesistest.data.movie.MovieResponseMapper
import com.example.genesistest.network.NetworkResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    private lateinit var api: MovieApi

    @Mock
    private lateinit var mapper: MovieResponseMapper

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = MovieRepository(api, mapper)
    }

    @Test
    fun `requestPopularMovie returnsExpectedResult`() = runBlocking {
        // Створюємо тестові дані
        val page = 1
        val movieResponse = MovieResponse(
            1, 10, listOf(
                ResultResponse(
                    id = 1,
                    adult = true,
                    backdropPath = "",
                    genreIds = mutableListOf(1, 2, 3),
                    originalTitle = "",
                    originalLanguage = "",
                    overview = "",
                    popularity = 7.0f,
                    posterPath = "",
                    releaseDate = "",
                    title = "",
                    video = true,
                    voteAverage = 120f,
                    voteCount = 120
                )
            )
        )/* створіть об'єкт MovieResponse з тестовими даними */
        val expectedUsualMovieResult = MovieResult.Success(
            page,
            pages = 10,
            result = listOf<MovieDetailEntity>()
        )/* створіть очікуваний результат */

        // Підготовка для підміни результату з api
        Mockito.`when`(api.getMovieList(page = page))
            .thenReturn(NetworkResponse.Success(movieResponse))

        // Підготовка для підміни результату з mapper
        Mockito.`when`(mapper.mapMovieResponse(NetworkResponse.Success(movieResponse)))
            .thenReturn(expectedUsualMovieResult)

        // Виклик методу, який ми хочемо протестувати
        val result = repository.requestPopularMovie(page)

        // Перевіряємо, чи повернутий результат співпадає з очікуваним
        assertEquals(expectedUsualMovieResult, result)
    }
}