package android.example.feature_favourite_movies_screen.data.repository

import android.example.core.item.MovieItem
import android.example.feature_favourite_movies_screen.data.db.FavouriteMoviesDao
import android.example.feature_favourite_movies_screen.data.mapper.FavouriteMoviesMapper

class FavouriteMoviesRepositoryImpl(
    private val favouriteMoviesDao: FavouriteMoviesDao,
    private val favouriteMoviesMapper: FavouriteMoviesMapper
) : FavouriteMoviesRepository {
    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        favouriteMoviesMapper.mapListFavouriteMovieModelToListEntity(list = favouriteMoviesDao.getAllFavouriteMovies())
}