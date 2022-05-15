package android.example.movies.data.mapper

import android.example.movies.data.db.model.FavouriteMovieModel
import android.example.movies.domain.item.FavouriteMovieItem

class FavouriteMovieMapper {

    fun mapEntityToFavouriteMovieModel(
        favouriteMovieItem: FavouriteMovieItem
    ) = FavouriteMovieModel(
        movieId = favouriteMovieItem.movieId,
        title = favouriteMovieItem.title,
        titleOriginal = favouriteMovieItem.titleOriginal,
        description = favouriteMovieItem.description,
        ifVideo = favouriteMovieItem.ifVideo,
        rating = favouriteMovieItem.rating,
        releaseDate = favouriteMovieItem.releaseDate,
        smallPoster = favouriteMovieItem.smallPoster,
        bigPoster = favouriteMovieItem.bigPoster
    )

    private fun mapFavouriteMovieModelToEntity(
        favouriteMovieModel: FavouriteMovieModel
    ) = FavouriteMovieItem(
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

    fun mapListFavouriteMovieModelToListEntity(
        list: List<FavouriteMovieModel>
    ) = list.map {
        mapFavouriteMovieModelToEntity(favouriteMovieModel = it)
    }

}