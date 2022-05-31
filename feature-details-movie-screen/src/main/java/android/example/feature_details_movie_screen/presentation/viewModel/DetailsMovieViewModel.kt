package android.example.feature_details_movie_screen.presentation.viewModel

import android.example.core.const.OPEN_BY_FAVOURITE_MOVIES
import android.example.core.const.OPEN_BY_MOVIES
import android.example.core.eventArgs.ThrowableEventArgs
import android.example.core.item.MovieItem
import android.example.core.viewModel.BaseViewModel
import android.example.feature_details_movie_screen.domain.DetailsMovieUseCase
import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DetailsMovieViewModel(
    private val detailsMovieUseCase: DetailsMovieUseCase
) : BaseViewModel() {

    private val _commentsMovie: MutableLiveData<List<CommentMovieItem>> = MutableLiveData()
    private val _videosMovie: MutableLiveData<List<VideoMovieItem>> = MutableLiveData()
    private val _movie: MutableLiveData<MovieItem> = MutableLiveData()

    val commentsMovie: LiveData<List<CommentMovieItem>> = _commentsMovie
    val videosMovie: LiveData<List<VideoMovieItem>> = _videosMovie
    val movie: LiveData<MovieItem> = _movie

    private val lang: String = Locale.getDefault().language
    private lateinit var openBy: String
    private var movieId: Int = -1

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.IO) {
            postValueVideosMovie()
            postValueCommentsMovie()
            getMovieOpenBy()
        }
        setErrorInternetNotification(throwable)
    }

    fun loadingVideosCommentsMovie(movieId: Int, openBy: String) {
        this.movieId = movieId
        this.openBy = openBy

        viewModelScope.launch(exceptionHandler) {
            progressBarBase.value = true

            val jobVideosMovie = launch(Dispatchers.IO) {
                detailsMovieUseCase.getVideosMovieApi(movieId, lang)
                postValueVideosMovie()
            }

            val jobCommentsMovie = launch(Dispatchers.IO) {
                detailsMovieUseCase.getCommentsMovieApi(movieId, lang)
                postValueCommentsMovie()
            }

            val jobFavouriteMovies = launch(Dispatchers.IO) {
                favouriteMoviesBase.postValue(detailsMovieUseCase.getAllFavouriteMoviesDB())
            }

            val jobMovie = launch(Dispatchers.IO) {
                getMovieOpenBy()
            }

            jobVideosMovie.join()
            jobCommentsMovie.join()
            jobFavouriteMovies.join()
            jobMovie.join()

            progressBarBase.value = false
        }
    }

    fun addFavouriteMovieDB(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsMovieUseCase.addFavouriteMovieDB(movieItem)
            favouriteMoviesBase.postValue(detailsMovieUseCase.getAllFavouriteMoviesDB())
        }
    }

    fun deleteFavouriteMovieDB(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsMovieUseCase.deleteFavouriteMovieDB(movieId)
            favouriteMoviesBase.postValue(detailsMovieUseCase.getAllFavouriteMoviesDB())
        }
    }

    private fun setErrorInternetNotification(error: Throwable) {
        errorInternetNotificationBase.postValue(ThrowableEventArgs(error))
        progressBarBase.postValue(false)
    }

    private suspend fun postValueVideosMovie() {
        _videosMovie.postValue(detailsMovieUseCase.getVideosMovieDB(movieId))
    }

    private suspend fun postValueCommentsMovie() {
        _commentsMovie.postValue(detailsMovieUseCase.getCommentsMovieDB(movieId))
    }

    private suspend fun getMovieOpenBy() {
        when (openBy) {
            OPEN_BY_MOVIES -> {
                _movie.postValue(detailsMovieUseCase.getMovieDB(movieId))
            }

            OPEN_BY_FAVOURITE_MOVIES -> {
                _movie.postValue(detailsMovieUseCase.getFavouriteMovieDB(movieId))
            }
        }
    }
}