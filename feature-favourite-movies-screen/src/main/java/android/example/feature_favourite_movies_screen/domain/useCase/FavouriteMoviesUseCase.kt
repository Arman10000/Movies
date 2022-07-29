package android.example.feature_favourite_movies_screen.domain.useCase

import android.example.core.item.MovieItem

interface FavouriteMoviesUseCase {
    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>
}