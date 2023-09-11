package com.example.genesistest.ui

import com.example.genesistest.common.State
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.movie.SavedMovieRepository
import com.example.genesistest.ui.movie.saved.SavedMovieViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SavedMovieViewModelTest {

//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dispatcherIo: CoroutineDispatcher

    @Mock
    private lateinit var savedMovieRepository: SavedMovieRepository

    private lateinit var viewModel: SavedMovieViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SavedMovieViewModel(dispatcherIo, savedMovieRepository)
    }

    @Test
    fun `viewModelNotNull`() {
        assertNotNull(viewModel)
    }

    @Test
    fun `viewModelHandlesEmptyState`() = runTest {
        val emptyData = emptyList<MovieDetailEntity>()

        Mockito.`when`(savedMovieRepository.allMovie).thenReturn(flow {
            emit(emptyData)
        })

        val states = viewModel.savedMovie.toList()
        assertEquals(states.last(), State.Empty)
    }

    @Test
    fun `viewModelHandlesErrorState`() = runTest {
        val errorMessage = "Помилка завантаження даних"

        Mockito.`when`(savedMovieRepository.allMovie).thenReturn(flow {
            throw RuntimeException(errorMessage)
        })

        val states = viewModel.savedMovie.toList()
        assertEquals(states.last(), State.Error(errorMessage))
    }

    @Test
    fun `viewModelHandlesDataCorrectly`() = runTest {
        val entity = listOf(
            MovieDetailEntity(
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
        )

        Mockito.`when`(savedMovieRepository.allMovie).thenReturn(flow {
            emit(entity)
        })

        val states = viewModel.savedMovie.toList()
        assertEquals(states.last(), State.Content(entity))
    }
}

