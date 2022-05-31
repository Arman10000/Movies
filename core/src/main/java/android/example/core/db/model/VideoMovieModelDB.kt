package android.example.core.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_movie")
data class VideoMovieModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val urlVideo: String,
    val nameVideo: String
)