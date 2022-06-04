package android.example.core.db

import android.example.core.db.model.FavouriteMovieModel
import android.example.core.db.model.MovieModelDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

interface BaseDao {
    @Query("SELECT * FROM favourite_movies")
    suspend fun getAllFavouriteMovies(): List<FavouriteMovieModel>
}