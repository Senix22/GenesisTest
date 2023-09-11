package com.example.genesistest.ui.movie.actual.online

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.genesistest.common.State
import com.example.genesistest.data.models.MovieWithSavedStateEntity
import com.example.genesistest.ui.common.ErrorScreen
import com.example.genesistest.ui.common.LoadingProgress
import com.example.genesistest.ui.common.MovieItem
import com.example.genesistest.ui.movie.saved.SavedMovieViewModel


@Composable
fun Saved(
    viewModel: SavedMovieViewModel = hiltViewModel()
) {

    var movieList by remember {
        mutableStateOf<State<List<MovieWithSavedStateEntity>, String>?>(null)
    }
    LaunchedEffect(Unit) {
        viewModel.savedMovie.collect {
            movieList = it
        }
    }

    Column(
        modifier = Modifier
            .padding(
                top = 10.dp, start = 10.dp, end = 10.dp
            )
            .fillMaxWidth()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())

    ) {
        when (movieList) {
            is State.Content -> {
                (movieList as State.Content<List<MovieWithSavedStateEntity>>).data.forEach {
                    MovieItem(movie = it.entity)
                }
            }

            State.Empty -> {
                LoadingProgress()
            }

            is State.Error -> {
                ErrorScreen()
            }

            State.Loading -> {
                LoadingProgress()
            }

            else -> {
                LoadingProgress()
            }
        }
    }
}
