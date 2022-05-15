package android.example.movies.data.db

import android.example.movies.data.db.model.CommentsMovieModelDB
import android.example.movies.data.db.model.FavouriteMovieModel
import android.example.movies.data.db.model.MovieModelDB
import android.example.movies.data.db.model.VideoMovieModelDB
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieModelDB>>

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouriteMovies(): LiveData<List<FavouriteMovieModel>>

    @Query("SELECT * FROM comments_movie WHERE movieId == :movieId")
    suspend fun getCommentsMovie(movieId: Int): List<CommentsMovieModelDB>

    @Query("SELECT * FROM video_movie WHERE movieId == :movieId")
    suspend fun getVideoMovie(movieId: Int): List<VideoMovieModelDB>


    @Insert
    suspend fun addMovies(movies: List<MovieModelDB>)

    @Insert
    suspend fun addCommentsMovie(comments: List<CommentsMovieModelDB>)

    @Insert
    suspend fun addVideosMovie(video: List<VideoMovieModelDB>)

    @Insert
    suspend fun addFavouriteMovie(favouriteMovieModel: FavouriteMovieModel)

    @Query("DELETE FROM comments_movie WHERE movieId == :movieId")
    suspend fun deleteCommentsMovie(movieId: Int)

    @Query("DELETE FROM video_movie WHERE movieId == :movieId")
    suspend fun deleteVideosMovie(movieId: Int)

    @Query("DELETE FROM favourite_movies WHERE movieId == :movieId")
    suspend fun deleteFavouriteMovie(movieId: Int)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}