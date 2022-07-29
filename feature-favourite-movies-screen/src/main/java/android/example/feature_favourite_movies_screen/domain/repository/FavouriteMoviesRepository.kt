package android.example.feature_favourite_movies_screen.domain.repository

import android.example.core.item.MovieItem

interface FavouriteMoviesRepository {
    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>
}