package android.example.feature_details_movie_screen.data.db

import android.example.core.db.BaseDao
import android.example.core.db.model.CommentsMovieModelDB
import android.example.core.db.model.FavouriteMovieModel
import android.example.core.db.model.MovieModelDB
import android.example.core.db.model.VideoMovieModelDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DetailsMovieDao: BaseDao {

    @Query("SELECT * FROM movies WHERE movieId == :movieId")
    suspend fun getMovie(movieId: Int): MovieModelDB

    @Query("SELECT * FROM favourite_movies WHERE movieId == :movieId")
    suspend fun getFavouriteMovie(movieId: Int): FavouriteMovieModel

    @Query("SELECT * FROM comments_movie WHERE movieId == :movieId")
    suspend fun getCommentsMovie(movieId: Int): List<CommentsMovieModelDB>

    @Query("SELECT * FROM video_movie WHERE movieId == :movieId")
    suspend fun getVideoMovie(movieId: Int): List<VideoMovieModelDB>

    @Insert
    suspend fun addFavouriteMovie(favouriteMovieModel: FavouriteMovieModel)

    @Insert
    suspend fun addCommentsMovie(comments: List<CommentsMovieModelDB>)

    @Insert
    suspend fun addVideosMovie(video: List<VideoMovieModelDB>)

    @Query("DELETE FROM comments_movie WHERE movieId == :movieId")
    suspend fun deleteCommentsMovie(movieId: Int)

    @Query("DELETE FROM video_movie WHERE movieId == :movieId")
    suspend fun deleteVideosMovie(movieId: Int)

    @Query("DELETE FROM favourite_movies WHERE movieId == :movieId")
    suspend fun deleteFavouriteMovie(movieId: Int)
}