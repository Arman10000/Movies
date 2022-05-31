package android.example.feature_details_movie_screen.domain

import android.example.core.item.MovieItem
import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem

interface DetailsMovieUseCase {

    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>

    suspend fun getMovieDB(movieId: Int): MovieItem

    suspend fun getFavouriteMovieDB(movieId: Int): MovieItem

    suspend fun getCommentsMovieApi(movieId: Int, lang: String)

    suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem>

    suspend fun getVideosMovieApi(movieId: Int, lang: String)

    suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem>

    suspend fun addFavouriteMovieDB(movieItem: MovieItem)

    suspend fun deleteFavouriteMovieDB(movieId: Int)
}