package android.example.domain.repository

import android.example.domain.item.CommentMovieItem
import android.example.domain.item.MovieItem
import android.example.domain.item.VideoMovieItem

interface MovieRepository {

    suspend fun getAllMoviesDB(): List<MovieItem>

    suspend fun getAllFavouriteMoviesDB(): List<MovieItem>

    suspend fun getMovieDB(movieId: Int): MovieItem

    suspend fun getFavouriteMovieDB(movieId: Int): MovieItem

    suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem>

    suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem>

    fun getMoviesApi(sortBy: String, page: Int, lang: String): List<MovieItem>

    fun getVideosMovieApi(movieId: Int, lang: String): List<VideoMovieItem>

    fun getCommentsMovieApi(movieId: Int, lang: String): List<CommentMovieItem>

    suspend fun addMoviesDB(movies: List<MovieItem>)

    suspend fun addFavouriteMovieDB(movieItem: MovieItem)

    suspend fun addCommentsMovieDB(comments: List<CommentMovieItem>, movieId: Int)

    suspend fun addVideosMovieDB(videos: List<VideoMovieItem>, movieId: Int)

    suspend fun deleteFavouriteMovieDB(movieId: Int)

    suspend fun deleteCommentsMovieDB(movieId: Int)

    suspend fun deleteVideosMovieDB(movieId: Int)

    suspend fun clearMoviesDB()

}