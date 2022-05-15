package android.example.movies.domain.item

data class FavouriteMovieItem(
    val movieId: Int,
    val title: String,
    val titleOriginal: String,
    val description: String,
    val ifVideo: Boolean,
    val rating: Double,
    val releaseDate: String,
    val smallPoster: String,
    val bigPoster: String
) {

    fun toMovieItem(): MovieItem {
        return MovieItem(
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