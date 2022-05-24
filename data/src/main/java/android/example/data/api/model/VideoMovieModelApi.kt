package android.example.data.api.model

import com.google.gson.annotations.SerializedName

data class VideoMovieModelApi(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val nameVideo: String
)