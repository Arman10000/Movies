package android.example.movies.presentation.viewModel

import android.example.domain.item.CommentMovieItem
import android.example.domain.item.MovieItem
import android.example.domain.item.VideoMovieItem
import android.example.domain.useCase.MovieUseCase
import android.example.movies.presentation.eventArgs.ThrowableEventArgs
import android.example.movies.presentation.screen.OPEN_BY_FAVOURITE_MOVIES
import android.example.movies.presentation.screen.OPEN_BY_MOVIES
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ar2code.mutableliveevent.MutableLiveEvent
import java.util.*

class DetailsMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _commentsMovie: MutableLiveData<List<CommentMovieItem>> = MutableLiveData()
    private val _videosMovie: MutableLiveData<List<VideoMovieItem>> = MutableLiveData()
    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val _errorInternetNotification: MutableLiveEvent<ThrowableEventArgs> = MutableLiveEvent()
    private val _favouriteMovies: MutableLiveData<List<MovieItem>> = MutableLiveData()
    private val _movie: MutableLiveData<MovieItem> = MutableLiveData()

    val commentsMovie: LiveData<List<CommentMovieItem>> = _commentsMovie
    val videosMovie: LiveData<List<VideoMovieItem>> = _videosMovie
    val progressBar: LiveData<Boolean> = _progressBar
    val errorInternetNotification: LiveData<ThrowableEventArgs> = _errorInternetNotification
    val favouriteMovies: LiveData<List<MovieItem>> = _favouriteMovies
    val movie: LiveData<MovieItem> = _movie

    private val lang: String = Locale.getDefault().language
    private var firstStartDetailsMovie = true
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
        if (firstStartDetailsMovie) {
            firstStartDetailsMovie = false
            this.movieId = movieId
            this.openBy = openBy

            viewModelScope.launch {
                _progressBar.value = true

                val jobVideosMovie = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    movieUseCase.getVideosMovieApi(movieId, lang)
                    postValueVideosMovie()
                }

                val jobCommentsMovie = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    movieUseCase.getCommentsMovieApi(movieId, lang)
                    postValueCommentsMovie()
                }

                val jobFavouriteMovies = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
                }

                val jobMovie = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    getMovieOpenBy()
                }

                jobVideosMovie.join()
                jobCommentsMovie.join()
                jobFavouriteMovies.join()
                jobMovie.join()

                _progressBar.value = false
            }
        }
    }

    fun addFavouriteMovieDB(movieItem: MovieItem) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.addFavouriteMovieDB(movieItem)
            _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
        }
    }

    fun deleteFavouriteMovieDB(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.deleteFavouriteMovieDB(movieId)
            _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
        }
    }

    private fun setErrorInternetNotification(error: Throwable) {
        _errorInternetNotification.postValue(ThrowableEventArgs(error))
        _progressBar.postValue(false)
    }

    private suspend fun postValueVideosMovie() {
        _videosMovie.postValue(movieUseCase.getVideosMovieDB(movieId))
    }

    private suspend fun postValueCommentsMovie() {
        _commentsMovie.postValue(movieUseCase.getCommentsMovieDB(movieId))
    }

    private suspend fun getMovieOpenBy() {
        when (openBy) {
            OPEN_BY_MOVIES -> {
                _movie.postValue(movieUseCase.getMovieDB(movieId))
            }

            OPEN_BY_FAVOURITE_MOVIES -> {
                _movie.postValue(movieUseCase.getFavouriteMovieDB(movieId))
            }
        }
    }
}