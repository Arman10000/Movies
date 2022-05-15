package android.example.movies.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideosMovieList(
    @SerializedName("results") @Expose
    val videosMovieList: List<VideoMovieModelApi>
)