package android.example.feature_details_movie_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class VideosMovieList(
    @SerializedName("results")
    val videosMovieList: List<VideoMovieModelApi>
)