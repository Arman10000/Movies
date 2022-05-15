package android.example.movies.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results") @Expose
    val moviesList: List<MovieModelApi>
)