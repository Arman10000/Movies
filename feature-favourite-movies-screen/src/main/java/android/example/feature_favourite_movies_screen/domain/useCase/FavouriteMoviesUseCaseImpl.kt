package android.example.feature_favourite_movies_screen.domain.useCase

import android.example.core.item.MovieItem
import android.example.feature_favourite_movies_screen.domain.repository.FavouriteMoviesRepository

class FavouriteMoviesUseCaseImpl(
    private val FavouriteMoviesRepository: FavouriteMoviesRepository
) : FavouriteMoviesUseCase {

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        FavouriteMoviesRepository.getAllFavouriteMoviesDB()
}