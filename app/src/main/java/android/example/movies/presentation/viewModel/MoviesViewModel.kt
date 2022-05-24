package android.example.movies.presentation.viewModel

import android.example.data.api.MoviesApi
import android.example.domain.item.MovieItem
import android.example.domain.useCase.MovieUseCase
import android.example.movies.presentation.enum.TypeSortEnum
import android.example.movies.presentation.eventArgs.ThrowableEventArgs
import android.example.movies.presentation.state.StateSelectedTypeSortMovies
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ar2code.mutableliveevent.MutableLiveEvent
import java.util.*

class MoviesViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _progressBar: MutableLiveData<Boolean> = MutableLiveData()
    private val _setSelectedTypeSortMovies: MutableLiveData<StateSelectedTypeSortMovies> = MutableLiveData()
    private val _errorInternetNotification: MutableLiveEvent<ThrowableEventArgs> = MutableLiveEvent()
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData()

    val progressBar: LiveData<Boolean> = _progressBar
    val setSelectedTypeSortMovies: LiveData<StateSelectedTypeSortMovies> = _setSelectedTypeSortMovies
    val errorInternetNotification: LiveData<ThrowableEventArgs> = _errorInternetNotification
    val movies: LiveData<List<MovieItem>> = _movies

    private var typeSort: String = MoviesApi.SORT_BY_POPULARITY
    private var page = 1
    private val lang: String = Locale.getDefault().language
    private var isErrorInternet = false
    private var isLoading = false
    private var firstStartMovies = true

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
            (visibleItemCount + showItemCount) >= totalItemCount &&
            !isLoading
        ) {
            if (!isErrorInternet) page++
            startLoadingMovies()
        }
    }

    private fun stopLoadingMovies() {
        isErrorInternet = false
        isLoading = false
    }

    private fun startLoadingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _progressBar.postValue(true)
            isLoading = true

            val result = movieUseCase.startLoadingMovies(typeSort, page, lang)

            if (result.isFailure) {
                setErrorInternetNotification(error = result.exceptionOrNull())
                stopLoadingMovies()
            }

            _movies.postValue(movieUseCase.getAllMoviesDB())

            _progressBar.postValue(false)
            isLoading = false
        }
    }

    fun loadingMoviesBySelectedTypeSort(
        typeSortEnum: TypeSortEnum
    ) {
        typeSort = when (typeSortEnum) {
            TypeSortEnum.SWITCH_SORT -> {
                if (typeSort == MoviesApi.SORT_BY_POPULARITY)
                    MoviesApi.SORT_BY_TOP_RATED
                else
                    MoviesApi.SORT_BY_POPULARITY
            }

            TypeSortEnum.TEXT_POPULARITY -> {
                MoviesApi.SORT_BY_POPULARITY
            }

            TypeSortEnum.TEXT_TOP_RATED -> {
                MoviesApi.SORT_BY_TOP_RATED
            }
        }

        _setSelectedTypeSortMovies.value = StateSelectedTypeSortMovies(
            typeSort == MoviesApi.SORT_BY_TOP_RATED
        )
        page = 1
        startLoadingMovies()
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