package android.example.core.db

import android.example.core.db.model.CommentsMovieModelDB
import android.example.core.db.model.FavouriteMovieModelDB
import android.example.core.db.model.MovieModelDB
import android.example.core.db.model.VideoMovieModelDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieModelDB>

    @Insert
    fun addMovies(movies: List<MovieModelDB>)

    @Query("DELETE FROM movies")
    fun clearMovies()

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouriteMovies(): List<FavouriteMovieModelDB>

    @Query("SELECT * FROM movies WHERE movieId == :movieId")
    fun getMovie(movieId: Int): MovieModelDB

    @Query("SELECT * FROM favourite_movies WHERE movieId == :movieId")
    fun getFavouriteMovie(movieId: Int): FavouriteMovieModelDB

    @Query("SELECT * FROM comments_movie WHERE movieId == :movieId")
    fun getCommentsMovie(movieId: Int): List<CommentsMovieModelDB>

    @Query("SELECT * FROM video_movie WHERE movieId == :movieId")
    fun getVideoMovie(movieId: Int): List<VideoMovieModelDB>

    @Insert
    fun addFavouriteMovie(favouriteMovieModel: FavouriteMovieModelDB)

    @Insert
    fun addCommentsMovie(comments: List<CommentsMovieModelDB>)

    @Insert
    fun addVideosMovie(video: List<VideoMovieModelDB>)

    @Query("DELETE FROM comments_movie WHERE movieId == :movieId")
    fun deleteCommentsMovie(movieId: Int)

    @Query("DELETE FROM video_movie WHERE movieId == :movieId")
    fun deleteVideosMovie(movieId: Int)

    @Query("DELETE FROM favourite_movies WHERE movieId == :movieId")
    fun deleteFavouriteMovie(movieId: Int)
}