package android.example.data.mapper

import android.example.data.api.MoviesApi
import android.example.data.api.model.MovieList
import android.example.data.api.model.MovieModelApi
import android.example.data.db.model.FavouriteMovieModel
import android.example.data.db.model.MovieModelDB
import android.example.domain.item.MovieItem
import retrofit2.Response

class MovieMapper {

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
        smallPoster = MoviesApi.BASE_POSTER_URL + MoviesApi.SMALL_POSTER_SIZE + movieModelApi.posterPath,
        bigPoster = MoviesApi.BASE_POSTER_URL + MoviesApi.BIG_POSTER_SIZE + movieModelApi.posterPath
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

    fun mapFavouriteMovieModelToEntity(
        favouriteMovieModel: FavouriteMovieModel
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

    fun mapEntityToFavouriteMovieModel(
        movieItem: MovieItem
    ) = FavouriteMovieModel(
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

    fun mapListFavouriteMovieModelToListEntity(
        list: List<FavouriteMovieModel>
    ) = list.map {
        mapFavouriteMovieModelToEntity(favouriteMovieModel = it)
    }
}