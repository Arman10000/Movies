package android.example.feature_details_movie_screen.data.mapper

import android.example.core.db.model.FavouriteMovieModelDB
import android.example.core.item.MovieItem
import android.example.core.mapper.BaseMapper

class DetailsMovieMapper : BaseMapper() {

    fun mapEntityToFavouriteMovieModel(
        movieItem: MovieItem
    ) = FavouriteMovieModelDB(
        movieId = movieItem.movieId,
        title = movieItem.title,
        titleOriginal = movieItem.titleOriginal,
        description = movieItem.description,
        ifVideo = movieItem.ifVideo,
        rating = movieItem.rating,
        releaseDate = movieItem.releaseDate,
        smallPoster = movieItem.smallPoster,
        bigPoster = movieItem.bigPoster
    )
}