package android.example.feature_favourite_movies_screen.domain

import android.example.core.item.MovieItem

interface FavouriteMoviesUseCase {
    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>
}