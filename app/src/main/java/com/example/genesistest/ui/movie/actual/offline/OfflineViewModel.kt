package com.example.genesistest.ui.movie.actual.offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.movie.OfflineMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OfflineViewModel @Inject constructor(
    private val db: OfflineMovieRepository
) : ViewModel() {
    private val _savedNews: MutableStateFlow<List<MovieDetailEntity>> =
        MutableStateFlow(emptyList())
    val savedNews: Flow<List<MovieDetailEntity>> = _savedNews

    init {
        getOfflineData()
    }

    private fun getOfflineData() {
        viewModelScope.launch {
            db.allOfflineMovie
                .collect {
                    _savedNews.value = it
                }
        }
    }
}