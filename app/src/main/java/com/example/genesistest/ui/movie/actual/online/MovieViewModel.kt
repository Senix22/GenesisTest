package com.example.genesistest.ui.movie.actual.online

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.genesistest.common.State
import com.example.genesistest.common.toMonthName
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.movie.OfflineMovieRepository

import com.example.genesistest.data.page.MovieSource

import com.example.genesistest.di.DispatcherIo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    @DispatcherIo private val workDispatcher: CoroutineDispatcher,
    private val movieSource: MovieSource,
    private val db: OfflineMovieRepository
) : ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<UiModel>?>(null)
    val movieList: Flow<PagingData<UiModel>> = _movieList
        .filterNotNull()
    private val _offlineMovieList = MutableStateFlow<List<MovieDetailEntity>?>(null)
    val offlineMovieList: Flow<List<MovieDetailEntity>> = _offlineMovieList
        .filterNotNull()

    private val _state = MutableStateFlow<State<Any, Any>>(State.Loading)
    val state: Flow<State<Any, Any>> = _state.onEach { delay(500L) }


    fun getMovie(isConnected: Boolean) {
        if (isConnected) {
            viewModelScope.launch {
                Pager(PagingConfig(MOVIE_PAGE_SIZE)) { movieSource }.flow
                    .cachedIn(this)
                    .catch { e ->
                        _state.value = State.Error(Any())
                    }
                    .map { paging -> paging.map { UiModel.MovieItem(it) } }
                    .map {
                        it.insertSeparators { before, after ->
                            if (after == null) {
                                return@insertSeparators null
                            }
                            if (before == null) {
                                return@insertSeparators UiModel.SeparatorItem("${after.movie.releaseDate?.toMonthName()}")
                            }
                            val currentMonth = after.movie.releaseDate?.toMonthName()
                            val previousMonth = before.movie.releaseDate?.toMonthName()

                            if (currentMonth != previousMonth) {
                                UiModel.SeparatorItem("${after.movie.releaseDate?.toMonthName()}")
                            } else {
                                // no separator
                                null
                            }
                        }
                    }
                    .flowOn(workDispatcher)
                    .collect {
                        _state.value = State.Content(it)
                        _movieList.emit(it)
                    }
            }
        } else {
            viewModelScope.launch {
                db.allOfflineMovie.collect {
                    _offlineMovieList.emit(it)
                    _state.value = State.Empty

                }

            }


        }
    }

    companion object {
        const val MOVIE_PAGE_SIZE = 10
    }
}


sealed class UiModel {
    data class MovieItem(val movie: MovieDetailEntity) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}