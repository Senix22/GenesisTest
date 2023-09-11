package com.example.genesistest.ui.movie.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.movie.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedWidgetViewModel @Inject constructor(
    private val savedNewsRepository: SavedMovieRepository,
) : ViewModel() {

    fun changeState(newsEntity: MovieDetailEntity, stateToChange: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            if (stateToChange)
                savedNewsRepository.add(newsEntity)
            else
                savedNewsRepository.remove(newsEntity)
        }

    fun collectIsSaved(newsEntity: MovieDetailEntity): Flow<Boolean> =
        savedNewsRepository.collectIsSaved(newsEntity)
}
