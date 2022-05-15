package android.example.movies.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_movie")
data class CommentsMovieModelDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val nameAuthor: String,
    val comment: String
)