package android.example.feature_details_movie_screen.data.db

import android.example.core.db.model.CommentsMovieModelDB
import android.example.core.db.model.FavouriteMovieModel
import android.example.core.db.model.MovieModelDB
import android.example.core.db.model.VideoMovieModelDB
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieModelDB::class,
        FavouriteMovieModel::class,
        CommentsMovieModelDB::class,
        VideoMovieModelDB::class
    ],
    version = 7,
    exportSchema = false
)
abstract class DetailsMovieDatabase: RoomDatabase() {
    abstract fun getDetailsMovieDao(): DetailsMovieDao
}