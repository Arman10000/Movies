package android.example.feature_details_movie_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class VideoMovieModelApi(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val nameVideo: String
)