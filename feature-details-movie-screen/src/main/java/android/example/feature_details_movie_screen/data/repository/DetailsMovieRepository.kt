package android.example.feature_details_movie_screen.data.repository

import android.example.core.item.MovieItem
import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem

interface DetailsMovieRepository {

    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>

    suspend fun getMovieDB(movieId: Int): MovieItem

    suspend fun getFavouriteMovieDB(movieId: Int): MovieItem

    suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem>

    suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem>

    fun getVideosMovieApi(movieId: Int, lang: String): List<VideoMovieItem>

    fun getCommentsMovieApi(movieId: Int, lang: String): List<CommentMovieItem>

    suspend fun addFavouriteMovieDB(movieItem: MovieItem)

    suspend fun addCommentsMovieDB(comments: List<CommentMovieItem>, movieId: Int)

    suspend fun addVideosMovieDB(videos: List<VideoMovieItem>, movieId: Int)

    suspend fun deleteFavouriteMovieDB(movieId: Int)

    suspend fun deleteCommentsMovieDB(movieId: Int)

    suspend fun deleteVideosMovieDB(movieId: Int)
}