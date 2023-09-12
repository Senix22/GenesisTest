package com.example.genesistest.ui.movie.actual.online

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.genesistest.common.State
import com.example.genesistest.common.isNetworkAvailable
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.ui.common.ErrorScreen
import com.example.genesistest.ui.common.LoadingProgress
import com.example.genesistest.ui.common.MovieItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay


@Composable
fun MovieList() {
    MovieScreen()
}

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel()
) {

    var refreshing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(refreshing) {
        viewModel.getMovie(context.isNetworkAvailable())
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }

    val movieList = viewModel.movieList.collectAsLazyPagingItems()

    var offline by remember {
        mutableStateOf<List<MovieDetailEntity>>(emptyList())
    }
    LaunchedEffect(Unit) {
        viewModel.offlineMovieList.collect {
            offline = it
        }
    }

    val state by viewModel.state.collectAsState(initial = State.Loading)

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { viewModel.getMovie(context.isNetworkAvailable()) },
    ) {
        when (state) {
            is State.Content -> {

                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(movieList.itemCount) { index ->
                        val item = movieList[index]
                        if (item != null) {
                            when (item) {
                                is UiModel.MovieItem -> {
                                    MovieItem(item.movie)
                                }

                                is UiModel.SeparatorItem -> {
                                    Divider(color = Color.Gray, thickness = 1.dp)
                                    Text(text = item.description)
                                }
                            }

                        }
                    }
                }
            }


            State.Empty -> {
                LazyColumn {
                    items(offline.size) { index ->
                        val item = offline[index]
                        MovieItem(movie = item)
                    }
                }
            }

            is State.Error -> {
                ErrorScreen()
            }

            State.Loading -> {
                LoadingProgress()
            }
        }
    }

}
