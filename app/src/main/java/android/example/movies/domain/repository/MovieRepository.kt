package android.example.movies.domain.repository

import android.example.movies.data.api.model.CommentsMovieList
import android.example.movies.data.api.model.MovieList
import android.example.movies.data.api.model.VideosMovieList
import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.domain.item.VideoMovieItem
import androidx.lifecycle.LiveData
import retrofit2.Response

interface MovieRepository {

    fun getAllMoviesDB(): LiveData<List<MovieItem>>

    fun getAllFavouriteMoviesDB(): LiveData<List<FavouriteMovieItem>>

    suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem>

    suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem>

    fun getMoviesApi(
        sortBy: String,
        page: Int,
        lang: String
    ): Response<MovieList>

    fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): Response<VideosMovieList>

    fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): Response<CommentsMovieList>

    suspend fun addMoviesDB(movies: List<MovieItem>)

    suspend fun addFavouriteMovieDB(favouriteMovieItem: FavouriteMovieItem)

    suspend fun addCommentsMovieDB(
        comments: List<CommentMovieItem>,
        movieId: Int
    )

    suspend fun addVideosMovieDB(
        videos: List<VideoMovieItem>,
        movieId: Int
    )

    suspend fun deleteFavouriteMovieDB(movieId: Int)

    suspend fun deleteCommentsMovieDB(movieId: Int)

    suspend fun deleteVideosMovieDB(movieId: Int)

    suspend fun clearMoviesDB()

}