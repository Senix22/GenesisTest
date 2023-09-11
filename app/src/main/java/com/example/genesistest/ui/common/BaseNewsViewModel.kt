package com.example.genesistest.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.movie.SavedMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseNewsViewModel(private val savedMovieRepository: SavedMovieRepository) : ViewModel() {

    fun handleMovieSaving(entity: MovieDetailEntity, isSaved: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isSaved) {
                savedMovieRepository.add(entity)
            } else {
                savedMovieRepository.remove(entity)
            }
        }
    }
}
