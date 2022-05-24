package android.example.data.api.model

import com.google.gson.annotations.SerializedName

data class CommentsMovieList(
    @SerializedName("results")
    val commentsMovieList: List<CommentsMovieModelApi>
)