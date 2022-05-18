package android.example.movies.data.api.model

import android.example.movies.data.api.MoviesApi
import android.example.movies.domain.item.MovieItem
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
) {

    fun toMovieItem() = MovieItem(
        movieId = this.movieId,
        title = this.title,
        titleOriginal = this.titleOriginal,
        description = this.description,
        ifVideo = this.ifVideo,
        rating = this.rating,
        releaseDate = this.releaseDate,
        smallPoster = MoviesApi.BASE_POSTER_URL + MoviesApi.SMALL_POSTER_SIZE + this.posterPath,
        bigPoster = MoviesApi.BASE_POSTER_URL + MoviesApi.BIG_POSTER_SIZE + this.posterPath
    )

}