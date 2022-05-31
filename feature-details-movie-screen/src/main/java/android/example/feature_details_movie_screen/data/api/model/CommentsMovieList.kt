package android.example.feature_details_movie_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class CommentsMovieList(
    @SerializedName("results")
    val commentsMovieList: List<CommentsMovieModelApi>
)