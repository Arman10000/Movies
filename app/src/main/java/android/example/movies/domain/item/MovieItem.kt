package android.example.movies.domain.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItem(
    val movieId: Int,
    val title: String,
    val titleOriginal: String,
    val description: String,
    val ifVideo: Boolean,
    val rating: Double,
    val releaseDate: String,
    val smallPoster: String,
    val bigPoster: String
) : Parcelable {

    fun toFavouriteMovieItem(): FavouriteMovieItem {
        return FavouriteMovieItem(
            movieId = this.movieId,
            title = this.title,
            titleOriginal = this.titleOriginal,
            description = this.description,
            ifVideo = this.ifVideo,
            rating = this.rating,
            releaseDate = this.releaseDate,
            smallPoster = this.smallPoster,
            bigPoster = this.bigPoster
        )
    }
}