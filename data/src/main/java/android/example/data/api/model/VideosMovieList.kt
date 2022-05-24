package android.example.data.api.model

import com.google.gson.annotations.SerializedName

data class VideosMovieList(
    @SerializedName("results")
    val videosMovieList: List<VideoMovieModelApi>
)