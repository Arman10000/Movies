package android.example.core.mapper

import android.example.core.db.model.FavouriteMovieModelDB
import android.example.core.db.model.MovieModelDB
import android.example.core.item.MovieItem

abstract class BaseMapper {

    fun mapFavouriteMovieModelToEntity(
        favouriteMovieModel: FavouriteMovieModelDB
    ) = MovieItem(
        movieId = favouriteMovieModel.movieId,
        title = favouriteMovieModel.title,
        titleOriginal = favouriteMovieModel.titleOriginal,
        description = favouriteMovieModel.description,
        ifVideo = favouriteMovieModel.ifVideo,
        rating = favouriteMovieModel.rating,
        releaseDate = favouriteMovieModel.releaseDate,
        smallPoster = favouriteMovieModel.smallPoster,
        bigPoster = favouriteMovieModel.bigPoster
    )

    fun mapMovieModelDBToEntity(
        movieModelDB: MovieModelDB
    ) = MovieItem(
        movieModelDB.movieId,
        movieModelDB.title,
        movieModelDB.titleOriginal,
        movieModelDB.description,
        movieModelDB.ifVideo,
        movieModelDB.rating,
        movieModelDB.releaseDate,
        movieModelDB.smallPoster,
        movieModelDB.bigPoster
    )

    fun mapListFavouriteMovieModelToListEntity(
        list: List<FavouriteMovieModelDB>
    ) = list.map {
        mapFavouriteMovieModelToEntity(favouriteMovieModel = it)
    }
}