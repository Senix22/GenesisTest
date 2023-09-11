package com.example.genesistest.ui.common.tab

import androidx.compose.runtime.Composable
import com.example.genesistest.ui.movie.actual.online.MovieList
import com.example.genesistest.ui.movie.actual.online.Saved


data class TabItem(
    val title: String,
    val screen: @Composable () -> Unit
)


val tabs = listOf(
    TabItem(
        title = "Movie",
        screen = { MovieList() }
    ),
    TabItem(
        title = "Favorite",
        screen = { Saved() }
    ),
)