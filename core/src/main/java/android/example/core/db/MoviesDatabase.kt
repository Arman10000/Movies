package android.example.core.db

import android.app.Application
import android.example.core.db.model.CommentsMovieModelDB
import android.example.core.db.model.FavouriteMovieModelDB
import android.example.core.db.model.MovieModelDB
import android.example.core.db.model.VideoMovieModelDB
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieModelDB::class,
        FavouriteMovieModelDB::class,
        VideoMovieModelDB::class,
        CommentsMovieModelDB::class
    ],
    version = 9,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    companion object {
        private const val DB_NAME = "main.db"

        fun getInstance(
            application: Application
        ): MoviesDatabase = Room.databaseBuilder(application, MoviesDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}