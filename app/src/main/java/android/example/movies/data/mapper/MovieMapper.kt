package android.example.movies.data.mapper

import android.example.movies.data.db.model.MovieModelDB
import android.example.movies.domain.item.MovieItem

class MovieMapper {

    private fun mapMovieModelDBToEntity(
        movieModelDB: MovieModelDB
    ) = MovieItem(
        movieId = movieModelDB.movieId,
        title = movieModelDB.title,
        titleOriginal = movieModelDB.titleOriginal,
        description = movieModelDB.description,
        ifVideo = movieModelDB.ifVideo,
        rating = movieModelDB.rating,
        releaseDate = movieModelDB.releaseDate,
        smallPoster = movieModelDB.smallPoster,
        bigPoster = movieModelDB.bigPoster
    )

    private fun mapEntityToMovieModelDB(
        movieItem: MovieItem
    ) = MovieModelDB(
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

    fun mapListMovieModelToListEntity(
        list: List<MovieModelDB>
    ) = list.map {
        mapMovieModelDBToEntity(movieModelDB = it)
    }

    fun mapListEntityToListMovieModelDB(
        list: List<MovieItem>
    ) = list.map {
        mapEntityToMovieModelDB(movieItem = it)
    }

}