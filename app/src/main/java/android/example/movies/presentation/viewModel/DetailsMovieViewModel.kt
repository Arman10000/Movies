package android.example.movies.presentation.viewModel

import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.VideoMovieItem
import android.example.movies.domain.useCase.MovieUseCase
import android.example.movies.presentation.eventArgs.ThrowableEventArgs
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
): ViewModel() {

    private val _commentsMovie: MutableLiveData<List<CommentMovieItem>> = MutableLiveData()
    private val _videosMovie: MutableLiveData<List<VideoMovieItem>> = MutableLiveData()
    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val _errorInternetNotification: MutableLiveEvent<ThrowableEventArgs> = MutableLiveEvent()

    val commentsMovie: LiveData<List<CommentMovieItem>> = _commentsMovie
    val videosMovie: LiveData<List<VideoMovieItem>> = _videosMovie
    val progressBar: LiveData<Boolean> = _progressBar
    val errorInternetNotification: LiveData<ThrowableEventArgs> = _errorInternetNotification

    val favouriteMovies: LiveData<List<FavouriteMovieItem>> = movieUseCase.getAllFavouriteMoviesDB()

    private val lang: String = Locale.getDefault().language
    private var firstStartDetailsMovie = true

    fun loadingVideosCommentsMovie(
        movieId: Int
    ) {
        if (firstStartDetailsMovie) {
            firstStartDetailsMovie = false
            viewModelScope.launch {

                _progressBar.value = true

                val jobVideosMovie = viewModelScope.launch(Dispatchers.IO) {
                    val result = movieUseCase.getVideosMovieApi(
                        movieId = movieId,
                        lang = lang
                    )

                    if (result.isSuccess) {
                        _videosMovie.postValue(result.getOrNull())
                    } else {
                        _videosMovie.postValue(movieUseCase.getVideosMovieDB(movieId = movieId))
                        setErrorInternetNotification(error = result.exceptionOrNull())
                    }
                }

                val jobCommentsMovie = viewModelScope.launch(Dispatchers.IO) {
                    val result = movieUseCase.getCommentsMovieApi(
                        movieId = movieId,
                        lang = lang
                    )

                    if (result.isSuccess) {
                        _commentsMovie.postValue(result.getOrNull())
                    } else {
                        _commentsMovie.postValue(movieUseCase.getCommentsMovieDB(movieId = movieId))
                        setErrorInternetNotification(error = result.exceptionOrNull())
                    }
                }

                jobVideosMovie.join()
                jobCommentsMovie.join()

                _progressBar.value = false

            }

        }
    }

    fun addFavouriteMovieDB(
        favouriteMovieItem: FavouriteMovieItem
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.addFavouriteMovieDB(favouriteMovieItem = favouriteMovieItem)
        }
    }

    fun deleteFavouriteMovieDB(
        movieId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.deleteFavouriteMovieDB(movieId = movieId)
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