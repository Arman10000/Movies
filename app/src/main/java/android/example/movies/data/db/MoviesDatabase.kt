package android.example.movies.data.db

import android.app.Application
import android.example.movies.data.db.model.CommentsMovieModelDB
import android.example.movies.data.db.model.FavouriteMovieModel
import android.example.movies.data.db.model.MovieModelDB
import android.example.movies.data.db.model.VideoMovieModelDB
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieModelDB::class,
        FavouriteMovieModel::class,
        CommentsMovieModelDB::class,
        VideoMovieModelDB::class
    ],
    version = 6,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "main.db"

        fun getInstance(
            application: Application
        ): MoviesDatabase = Room.databaseBuilder(application, MoviesDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    }

    abstract fun movieDao(): MoviesDao
}