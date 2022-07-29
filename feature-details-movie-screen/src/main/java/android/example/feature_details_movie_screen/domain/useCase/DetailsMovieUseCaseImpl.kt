package android.example.feature_details_movie_screen.domain.useCase

import android.example.core.item.MovieItem
import android.example.feature_details_movie_screen.domain.repository.DetailsMovieRepository
import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem

class DetailsMovieUseCaseImpl(
    private val detailsMovieRepository: DetailsMovieRepository
) : DetailsMovieUseCase {

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        detailsMovieRepository.getAllFavouriteMoviesDB()

    override suspend fun getMovieDB(movieId: Int): MovieItem =
        detailsMovieRepository.getMovieDB(movieId)

    override suspend fun getFavouriteMovieDB(movieId: Int): MovieItem =
        detailsMovieRepository.getFavouriteMovieDB(movieId)

    override suspend fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ) {
        val commentsMovie = detailsMovieRepository.getCommentsMovieApi(movieId, lang)

        if (commentsMovie.isNotEmpty()) {
            detailsMovieRepository.deleteCommentsMovieDB(movieId)
            detailsMovieRepository.addCommentsMovieDB(commentsMovie, movieId)
        }
    }

    override suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem> =
        detailsMovieRepository.getCommentsMovieDB(movieId)

    override suspend fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ) {
        val videosMovie = detailsMovieRepository.getVideosMovieApi(movieId, lang)

        if (videosMovie.isNotEmpty()) {
            detailsMovieRepository.deleteVideosMovieDB(movieId)
            detailsMovieRepository.addVideosMovieDB(videosMovie, movieId)
        }
    }

    override suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem> =
        detailsMovieRepository.getVideosMovieDB(movieId)

    override suspend fun addFavouriteMovieDB(movieItem: MovieItem) {
        detailsMovieRepository.addFavouriteMovieDB(movieItem)
    }

    override suspend fun deleteFavouriteMovieDB(movieId: Int) {
        detailsMovieRepository.deleteFavouriteMovieDB(movieId)
    }
}