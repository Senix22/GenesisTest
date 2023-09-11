package com.example.genesistest.ui.common

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.genesistest.data.models.MovieDetailEntity
import com.example.genesistest.ui.movie.saved.SavedWidgetViewModel

@Composable
fun MovieItem(
    movie: MovieDetailEntity,
) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, movie.title)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    Row() {
        Column {
            ImageColumn(movie, modifier = Modifier.size(Dimen.image_item_size.dp))
        }
        Column(Modifier.padding(start = Dimen.normal_50.dp)) {
            Text(
                text = movie.title.toString(), Modifier
                    .padding(
                        top = Dimen.normal_50.dp,
                    )
            )
            SavedWidget(
                entity = movie, modifier = Modifier.padding(
                    top = Dimen.normal_50.dp,
                    bottom = Dimen.normal_50.dp
                )
            )
            Text(text = "Share", modifier = Modifier.clickable {
                context.startActivity(shareIntent)
            })

        }


    }

}


@Composable
fun SavedWidget(
    entity: MovieDetailEntity,
    modifier: Modifier,
    viewModel: SavedWidgetViewModel = hiltViewModel()
) {

    val state by viewModel.collectIsSaved(entity).collectAsState(initial = false)


    Text(text = if (state) "remove" else "like", modifier = modifier
        .clickable {
            viewModel.changeState(entity, !state)
        }
        .fillMaxWidth()
    )
}

@Composable
fun ErrorScreen() {
    Text(text = "Error")
}


