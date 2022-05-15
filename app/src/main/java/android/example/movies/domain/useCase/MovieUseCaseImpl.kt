package android.example.movies.domain.useCase

import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.domain.item.VideoMovieItem
import android.example.movies.domain.repository.MovieRepository
import androidx.lifecycle.LiveData

class MovieUseCaseImpl(
    private val movieRepository: MovieRepository
) : MovieUseCase {

    override fun getAllFavouriteMoviesDB(): LiveData<List<FavouriteMovieItem>> =
        movieRepository.getAllFavouriteMoviesDB()

    override fun getAllMoviesDB(): LiveData<List<MovieItem>> =
        movieRepository.getAllMoviesDB()

    override suspend fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<CommentMovieItem>> {

        var commentsMovie = listOf<CommentMovieItem>()

        try {
            val result = movieRepository.getCommentsMovieApi(movieId = movieId, lang = lang)
            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.commentsMovieList.isNotEmpty()) {
                        commentsMovie = it.commentsMovieList.map { commentsMovieModelApi ->
                            commentsMovieModelApi.toCommentsMovieItem()
                        }
                        movieRepository.deleteCommentsMovieDB(movieId = movieId)
                        movieRepository.addCommentsMovieDB(
                            comments = commentsMovie,
                            movieId = movieId
                        )
                    }
                }
            }

            return Result.success(commentsMovie)

        } catch (t: Throwable) {
            return Result.failure(t)
        }

    }

    override suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem> = movieRepository.getCommentsMovieDB(movieId = movieId)

    override suspend fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): Result<List<VideoMovieItem>> {

        var videosMovie = listOf<VideoMovieItem>()

        try {
            val result = movieRepository.getVideosMovieApi(movieId = movieId, lang = lang)

            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.videosMovieList.isNotEmpty()) {
                        videosMovie = it.videosMovieList.map { videoMovieModelApi ->
                            videoMovieModelApi.toVideoMovieItem()
                        }
                        movieRepository.deleteVideosMovieDB(movieId = movieId)
                        movieRepository.addVideosMovieDB(videos = videosMovie, movieId = movieId)
                    }
                }
            }

            return Result.success(videosMovie)

        } catch (t: Throwable) {
            return Result.failure(t)
        }

    }

    override suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem> = movieRepository.getVideosMovieDB(movieId = movieId)

    override suspend fun addFavouriteMovieDB(favouriteMovieItem: FavouriteMovieItem) {
        movieRepository.addFavouriteMovieDB(favouriteMovieItem = favouriteMovieItem)
    }

    override suspend fun deleteFavouriteMovieDB(movieId: Int) {
        movieRepository.deleteFavouriteMovieDB(movieId = movieId)
    }

    override suspend fun startLoadingMovies(
        sortBy: String,
        page: Int,
        lang: String
    ): Result<Boolean> {

        try {
            val result = movieRepository.getMoviesApi(sortBy = sortBy, page = page, lang = lang)

            if (result.isSuccessful) {
                result.body()?.let {
                    if (it.moviesList.isNotEmpty()) {
                        val movies = it.moviesList.map { movieModelApi ->
                            movieModelApi.toMovieItem()
                        }
                        if (page == 1) movieRepository.clearMoviesDB()
                        movieRepository.addMoviesDB(movies = movies)
                    }
                }

            }

            return Result.success(true)

        } catch (t: Throwable) {
            return Result.failure(t)
        }

    }
}