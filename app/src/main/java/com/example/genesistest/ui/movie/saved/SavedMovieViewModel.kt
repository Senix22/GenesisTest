package com.example.genesistest.ui.movie.saved

import androidx.lifecycle.viewModelScope
import com.example.genesistest.common.State
import com.example.genesistest.data.models.MovieWithSavedStateEntity
import com.example.genesistest.data.movie.SavedMovieRepository
import com.example.genesistest.di.DispatcherIo
import com.example.genesistest.ui.common.BaseNewsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMovieViewModel @Inject constructor(
    @DispatcherIo private val dispatcherIo: CoroutineDispatcher,
    private val savedMovieRepository: SavedMovieRepository
) : BaseNewsViewModel(savedMovieRepository) {
    private val _savedMovie: MutableStateFlow<State<List<MovieWithSavedStateEntity>, String>> =
        MutableStateFlow(State.Loading)
    val savedMovie: Flow<State<List<MovieWithSavedStateEntity>, String>> = _savedMovie

    init {
        _savedMovie.value = State.Loading
        viewModelScope.launch {
            savedMovieRepository.allMovie
                .map { list ->
                    list.map {
                        MovieWithSavedStateEntity(
                            isSaved = true,
                            entity = it
                        )
                    }
                }
                .catch {
                    _savedMovie.value = State.Error(it.message.toString())
                }
                .flowOn(dispatcherIo)
                .collect {
                    _savedMovie.value = if (it.isEmpty()) {
                        State.Empty
                    } else {
                        State.Content(it)
                    }
                }
        }
    }

}