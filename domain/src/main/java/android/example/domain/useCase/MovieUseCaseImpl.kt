package android.example.domain.useCase

import android.example.domain.item.CommentMovieItem
import android.example.domain.item.MovieItem
import android.example.domain.item.VideoMovieItem
import android.example.domain.repository.MovieRepository

class MovieUseCaseImpl(
    private val movieRepository: MovieRepository
) : MovieUseCase {

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        movieRepository.getAllFavouriteMoviesDB()

    override suspend fun getAllMoviesDB(): List<MovieItem> =
        movieRepository.getAllMoviesDB()

    override suspend fun getMovieDB(movieId: Int): MovieItem =
        movieRepository.getMovieDB(movieId)

    override suspend fun getFavouriteMovieDB(movieId: Int): MovieItem =
        movieRepository.getFavouriteMovieDB(movieId)

    override suspend fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<CommentMovieItem>?> {
        try {
            val commentsMovie = movieRepository.getCommentsMovieApi(movieId, lang)

            commentsMovie?.let {
                if (commentsMovie.isNotEmpty()) {
                    movieRepository.deleteCommentsMovieDB(movieId)
                    movieRepository.addCommentsMovieDB(commentsMovie, movieId)
                }
            }

            return Result.success(commentsMovie)

        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun getCommentsMovieDB(movieId: Int): List<CommentMovieItem> =
        movieRepository.getCommentsMovieDB(movieId)

    override suspend fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<VideoMovieItem>?> {
        try {
            val videosMovie = movieRepository.getVideosMovieApi(movieId, lang)

            videosMovie?.let {
                if (it.isNotEmpty()) {
                    movieRepository.deleteVideosMovieDB(movieId)
                    movieRepository.addVideosMovieDB(videosMovie, movieId)
                }
            }

            return Result.success(videosMovie)

        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    override suspend fun getVideosMovieDB(movieId: Int): List<VideoMovieItem> =
        movieRepository.getVideosMovieDB(movieId)

    override suspend fun addFavouriteMovieDB(movieItem: MovieItem) {
        movieRepository.addFavouriteMovieDB(movieItem)
    }

    override suspend fun deleteFavouriteMovieDB(movieId: Int) {
        movieRepository.deleteFavouriteMovieDB(movieId)
    }

    override suspend fun startLoadingMovies(
        sortBy: String,
        page: Int,
        lang: String
    ): Result<Boolean> {
        try {
            val movies = movieRepository.getMoviesApi(sortBy, page, lang)

            movies?.let {
                if (it.isNotEmpty()) {
                    if (page == 1) movieRepository.clearMoviesDB()
                    movieRepository.addMoviesDB(it)
                }
            }

            return Result.success(true)

        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }
}