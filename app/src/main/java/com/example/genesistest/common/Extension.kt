package com.example.genesistest.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.genesistest.data.api.ResultResponse
import com.example.genesistest.data.models.MovieDetailEntity
import java.text.SimpleDateFormat
import java.util.Locale

fun ResultResponse.asMovie() = MovieDetailEntity(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id ?: 0L,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)



 fun String.toMonthName(): String {
     val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
     val outputDateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
     val date = inputDateFormat.parse(this)
     return outputDateFormat.format(date)
}

fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
    } else {
        try {
            val activeNetworkInfo = manager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {

        }
    }
    return false
}