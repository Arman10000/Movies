package android.example.feature_favourite_movies_screen.data.repository

import android.example.core.db.MoviesDao
import android.example.core.item.MovieItem
import android.example.feature_favourite_movies_screen.data.mapper.FavouriteMoviesMapper
import android.example.feature_favourite_movies_screen.domain.repository.FavouriteMoviesRepository

class FavouriteMoviesRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val favouriteMoviesMapper: FavouriteMoviesMapper
) : FavouriteMoviesRepository {
    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        favouriteMoviesMapper.mapListFavouriteMovieModelToListEntity(list = moviesDao.getAllFavouriteMovies())
}