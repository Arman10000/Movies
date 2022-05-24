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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ar2code.mutableliveevent.MutableLiveEvent
import java.util.*

class DetailsMovieViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

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

    fun getMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.postValue(movieUseCase.getMovieDB(movieId))
        }
    }

    fun loadingVideosCommentsMovie(
        movieId: Int,
        openBy: String
    ) {
        if (firstStartDetailsMovie) {
            firstStartDetailsMovie = false
            viewModelScope.launch {

                _progressBar.value = true

                val jobVideosMovie = viewModelScope.launch(Dispatchers.IO) {
                    val result = movieUseCase.getVideosMovieApi(movieId, lang)

                    if (result.isSuccess) {
                        result.getOrNull()?.let {
                            if (it.isNotEmpty()) _videosMovie.postValue(it)
                        }
                    } else {
                        _videosMovie.postValue(movieUseCase.getVideosMovieDB(movieId))
                        setErrorInternetNotification(error = result.exceptionOrNull())
                    }
                }

                val jobCommentsMovie = viewModelScope.launch(Dispatchers.IO) {
                    val result = movieUseCase.getCommentsMovieApi(movieId, lang)

                    if (result.isSuccess) {
                        result.getOrNull()?.let {
                            if (it.isNotEmpty()) _commentsMovie.postValue(it)
                        }
                    } else {
                        _commentsMovie.postValue(movieUseCase.getCommentsMovieDB(movieId))
                        setErrorInternetNotification(error = result.exceptionOrNull())
                    }
                }

                val jobFavouriteMovies = viewModelScope.launch(Dispatchers.IO) {
                    _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
                }

                val jobMovie = viewModelScope.launch(Dispatchers.IO) {
                    when(openBy) {
                        OPEN_BY_MOVIES -> {
                            _movie.postValue(movieUseCase.getMovieDB(movieId))
                        }

                        OPEN_BY_FAVOURITE_MOVIES -> {
                            _movie.postValue(movieUseCase.getFavouriteMovieDB(movieId))
                        }
                    }
                }

                jobVideosMovie.join()
                jobCommentsMovie.join()
                jobFavouriteMovies.join()
                jobMovie.join()

                _progressBar.value = false
            }
        }
    }

    fun addFavouriteMovieDB(
        movieItem: MovieItem
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.addFavouriteMovieDB(movieItem)
            _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
        }
    }

    fun deleteFavouriteMovieDB(
        movieId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.deleteFavouriteMovieDB(movieId)
            _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
        }
    }

    private fun setErrorInternetNotification(
        error: Throwable?
    ) {
        error?.let {
            _errorInternetNotification.postValue(
                ThrowableEventArgs(error = it)
            )
        }
    }

}