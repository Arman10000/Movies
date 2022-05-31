package android.example.feature_favourite_movies_screen.domain

import android.example.core.item.MovieItem
import android.example.feature_favourite_movies_screen.data.repository.FavouriteMoviesRepository

class FavouriteMoviesUseCaseImpl(
    private val FavouriteMoviesRepository: FavouriteMoviesRepository
) : FavouriteMoviesUseCase {

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        FavouriteMoviesRepository.getAllFavouriteMoviesDB()
}