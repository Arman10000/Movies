package android.example.movies.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModelDB(
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