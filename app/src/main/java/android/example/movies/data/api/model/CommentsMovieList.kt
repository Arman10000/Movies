package android.example.movies.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentsMovieList(
    @SerializedName("results") @Expose
    val commentsMovieList: List<CommentsMovieModelApi>
)