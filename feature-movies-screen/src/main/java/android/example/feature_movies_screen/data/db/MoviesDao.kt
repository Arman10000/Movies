package android.example.feature_movies_screen.data.db

import android.example.core.db.BaseDao
import android.example.core.db.model.MovieModelDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieModelDB>

    @Insert
    suspend fun addMovies(movies: List<MovieModelDB>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()
}