package com.example.genesistest.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.genesistest.data.models.MovieDetailEntity


@Composable
fun ImageColumn(news: MovieDetailEntity, modifier: Modifier) {

    NewsImageBox("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+news.backdropPath.orEmpty(), modifier)
}

@Composable
fun NewsImageBox(newsImage: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(Dimen.image_item_size.dp)
            .clip(RectangleShape)
            .padding(top = Dimen.normal_100.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(newsImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = Dimen.single.dp, top = Dimen.single.dp)
                .fillMaxSize(),
        )
    }
}


