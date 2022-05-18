package android.example.movies.data.api.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val moviesList: List<MovieModelApi>
)