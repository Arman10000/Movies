package android.example.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class FavouriteMovieModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val title: String,
    val titleOriginal: String,
    val description: String,
    val ifVideo: Boolean,
    val rating: Double,
    val releaseDate: String,
    val smallPoster: String,
    val bigPoster: String
)