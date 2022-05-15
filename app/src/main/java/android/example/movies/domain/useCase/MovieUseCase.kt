package android.example.movies.domain.useCase

import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.domain.item.VideoMovieItem
import androidx.lifecycle.LiveData

interface MovieUseCase {

    fun getAllFavouriteMoviesDB(): LiveData<List<FavouriteMovieItem>>

    fun getAllMoviesDB(): LiveData<List<MovieItem>>

    suspend fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<CommentMovieItem>>

    suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem>

    suspend fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<VideoMovieItem>>

    suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem>

    suspend fun addFavouriteMovieDB(
        favouriteMovieItem: FavouriteMovieItem
    )

    suspend fun deleteFavouriteMovieDB(
        movieId: Int
    )

    suspend fun startLoadingMovies(
        sortBy: String,
        page: Int,
        lang: String
    ): Result<Boolean>
}