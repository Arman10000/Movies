package android.example.feature_movies_screen.data.mapper

import android.example.core.const.BASE_POSTER_URL
import android.example.core.const.BIG_POSTER_SIZE
import android.example.core.const.SMALL_POSTER_SIZE
import android.example.core.db.model.MovieModelDB
import android.example.core.item.MovieItem
import android.example.core.mapper.BaseMapper
import android.example.feature_movies_screen.data.api.model.MovieList
import android.example.feature_movies_screen.data.api.model.MovieModelApi
import retrofit2.Response

class MoviesMapper : BaseMapper() {

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

    private fun mapMovieModelApiToEntity(
        movieModelApi: MovieModelApi
    ) = MovieItem(
        movieModelApi.movieId,
        movieModelApi.title,
        movieModelApi.titleOriginal,
        movieModelApi.description,
        movieModelApi.ifVideo,
        movieModelApi.rating,
        movieModelApi.releaseDate,
        smallPoster = BASE_POSTER_URL + SMALL_POSTER_SIZE + movieModelApi.posterPath,
        bigPoster = BASE_POSTER_URL + BIG_POSTER_SIZE + movieModelApi.posterPath
    )

    fun mapListMovieModelDBToListEntity(
        list: List<MovieModelDB>
    ) = list.map {
        mapMovieModelDBToEntity(movieModelDB = it)
    }

    fun mapListEntityToListMovieModelDB(
        list: List<MovieItem>
    ) = list.map {
        mapEntityToMovieModelDB(movieItem = it)
    }

    fun mapListMovieModelApiToListEntity(
        response: Response<MovieList>
    ): List<MovieItem> {
        response.body()?.let {
            return it.moviesList.map { movieModelApi ->
                mapMovieModelApiToEntity(movieModelApi)
            }
        }

        return listOf()
    }
}