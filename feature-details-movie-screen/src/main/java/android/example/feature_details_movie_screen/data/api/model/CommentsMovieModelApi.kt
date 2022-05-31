package android.example.feature_details_movie_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class CommentsMovieModelApi(
    @SerializedName("author")
    val nameAuthor: String,
    @SerializedName("content")
    val comment: String
)