package android.example.domain.useCase

import android.example.domain.item.CommentMovieItem
import android.example.domain.item.MovieItem
import android.example.domain.item.VideoMovieItem

interface MovieUseCase {

    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>

    suspend fun getAllMoviesDB(): List<MovieItem>

    suspend fun getMovieDB(movieId: Int): MovieItem

    suspend fun getFavouriteMovieDB(movieId: Int): MovieItem

    suspend fun getCommentsMovieApi(movieId: Int, lang: String)

    suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem>

    suspend fun getVideosMovieApi(movieId: Int, lang: String)

    suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem>

    suspend fun addFavouriteMovieDB(movieItem: MovieItem)

    suspend fun deleteFavouriteMovieDB(movieId: Int)

    suspend fun startLoadingMovies(sortBy: String, page: Int, lang: String)
}