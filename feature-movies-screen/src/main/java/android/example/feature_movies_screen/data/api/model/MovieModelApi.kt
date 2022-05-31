package android.example.feature_movies_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class MovieModelApi(
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val titleOriginal: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("video")
    val ifVideo: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String
)