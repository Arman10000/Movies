package android.example.feature_movies_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val moviesList: List<MovieModelApi>
)