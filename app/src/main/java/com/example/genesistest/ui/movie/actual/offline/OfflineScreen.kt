package com.example.genesistest.ui.movie.actual.offline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.data.models.MovieWithSavedStateEntity
import com.example.genesistest.ui.common.Dimen
import com.example.genesistest.ui.common.LoadingProgress
import com.example.genesistest.ui.common.MovieItem
import com.example.genesistest.ui.movie.saved.SavedMovieViewModel

@Composable
fun OfflineScreen(
    viewModel: OfflineViewModel = hiltViewModel()
) {

    var movieList by remember {
        mutableStateOf<List<MovieDetailEntity>>(emptyList())
    }
    LaunchedEffect(Unit) {
        viewModel.savedNews.collect {
            movieList = it
        }
    }

    Column(
        modifier = Modifier
            .padding(
                top = Dimen.normal_50.dp, start = Dimen.normal_50.dp, end = Dimen.normal_50.dp
            )
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())

    ) {


       movieList.forEach {
           MovieItem(movie = it)
       }
    }
}
