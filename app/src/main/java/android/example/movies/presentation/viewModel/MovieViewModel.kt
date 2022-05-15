package android.example.movies.presentation.viewModel

import android.content.res.Resources
import android.example.movies.data.api.MoviesApi
import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.domain.item.VideoMovieItem
import android.example.movies.domain.useCase.MovieUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MovieViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    val movies: LiveData<List<MovieItem>> = movieUseCase.getAllMoviesDB()
    val favouriteMovies: LiveData<List<FavouriteMovieItem>> = movieUseCase.getAllFavouriteMoviesDB()
    private val _commentsMovie: MutableLiveData<List<CommentMovieItem>> = MutableLiveData()
    val commentsMovie: LiveData<List<CommentMovieItem>> = _commentsMovie
    private val _videosMovie: MutableLiveData<List<VideoMovieItem>> = MutableLiveData()
    val videosMovie: LiveData<List<VideoMovieItem>> = _videosMovie
    private val _errorInternetNotification: MutableLiveData<Throwable> = MutableLiveData()
    val errorInternetNotification: LiveData<Throwable> = _errorInternetNotification
    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData()
    val progressBar: LiveData<Boolean> = _progressBar

    var typeSort: String = MoviesApi.SORT_BY_POPULARITY
    private var page = 1
    private val lang: String = Locale.getDefault().language
    private var isErrorInternet = false
    private var isLoading = false
    private var firstStartMovies = true
    private var firstStartDetailMovie = true
    private var movieItem: MovieItem? = null

    fun loadingDetailMovie(
        movieId: Int
    ) {
        if (firstStartDetailMovie) {
            firstStartDetailMovie = false
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
                        _errorInternetNotification.postValue(result.exceptionOrNull())
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
                        _errorInternetNotification.postValue(result.exceptionOrNull())
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

    fun ifFirstStartMovies() {
        if (firstStartMovies) {
            firstStartMovies = false
            startLoadingMovies()
        }
    }

    fun loadingMoviesIfEndList(
        totalItemCount: Int,
        visibleItemCount: Int,
        showItemCount: Int
    ) {
        if (
            showItemCount > 0 &&
            (visibleItemCount + showItemCount) >= totalItemCount
            && !isLoading
        ) {
            if (!isErrorInternet) page++
            _progressBar.value = true
            startLoadingMovies()

        }

    }

    fun stopLoadingMovies() {
        isErrorInternet = false
        isLoading = false
    }

    private fun startLoadingMovies() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {

            val result = movieUseCase.startLoadingMovies(
                sortBy = typeSort,
                page = page,
                lang = lang
            )

            if (result.isFailure) {
                _errorInternetNotification.postValue(result.exceptionOrNull())
            }

            _progressBar.postValue(false)
        }
    }

    fun loadingMoviesBySelectedTypeSort(
        typeSort: String
    ) {
        this.typeSort = typeSort
        page = 1
        _progressBar.value = true
        startLoadingMovies()
    }

    fun getColumnCount(): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        val posterSize = MoviesApi.SMALL_POSTER_SIZE.toInt()
        return if (width / posterSize > 2) width / posterSize else 2
    }

    fun saveDetailsMovie(
        movieItem: MovieItem
    ) {
        this.movieItem = movieItem
        firstStartDetailMovie = true
    }

    fun getDetailsMovie(): MovieItem? = movieItem

}