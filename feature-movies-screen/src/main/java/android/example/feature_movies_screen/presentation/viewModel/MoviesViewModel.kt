package android.example.feature_movies_screen.presentation.viewModel

import android.example.core.item.MovieItem
import android.example.core.viewModel.BaseViewModel
import android.example.feature_movies_screen.data.api.MoviesApi.Companion.SORT_BY_POPULARITY
import android.example.feature_movies_screen.data.api.MoviesApi.Companion.SORT_BY_TOP_RATED
import android.example.feature_movies_screen.domain.useCase.MoviesUseCase
import android.example.feature_movies_screen.presentation.enum.TypeSortEnum
import android.example.feature_movies_screen.presentation.state.StateSelectedTypeSortMovies
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

class MoviesViewModel(
    private val moviesUseCase: MoviesUseCase
) : BaseViewModel() {

    private val _setSelectedTypeSortMovies: MutableLiveData<StateSelectedTypeSortMovies> =
        MutableLiveData()
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData()

    val setSelectedTypeSortMovies: LiveData<StateSelectedTypeSortMovies> =
        _setSelectedTypeSortMovies
    val movies: LiveData<List<MovieItem>> = _movies

    private var typeSort = SORT_BY_POPULARITY
    private var page = 1
    private val lang = Locale.getDefault().language
    private var isErrorInternet = false
    private var isLoading = false
    private var firstStartMovies = true

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(moviesUseCase.getAllMoviesDB())
        }
        viewModelScope.launch(Dispatchers.Main) {
            setErrorInternetNotification(throwable)
        }
    }

    fun ifFirstStartMovies() {
        if (firstStartMovies) {
            firstStartMovies = false
            startLoadingMovies()
        }
    }

    fun loadingMoviesIfEndList(totalItemCount: Int, visibleItemCount: Int, showItemCount: Int) {
        if (
            showItemCount > 0 &&
            (visibleItemCount + showItemCount) >= totalItemCount &&
            !isLoading
        ) {
            if (!isErrorInternet) page++
            startLoadingMovies()
        }
    }

    fun refreshLayout() {
        page = 1
        startLoadingMovies()
    }

    fun loadingMoviesBySelectedTypeSort(typeSortEnum: TypeSortEnum) {
        typeSort = when (typeSortEnum) {
            TypeSortEnum.SWITCH_SORT -> {
                if (typeSort == SORT_BY_POPULARITY)
                    SORT_BY_TOP_RATED
                else
                    SORT_BY_POPULARITY
            }

            TypeSortEnum.TEXT_POPULARITY -> {
                SORT_BY_POPULARITY
            }

            TypeSortEnum.TEXT_TOP_RATED -> {
                SORT_BY_TOP_RATED
            }
        }

        _setSelectedTypeSortMovies.value = StateSelectedTypeSortMovies(
            typeSort == SORT_BY_TOP_RATED
        )
        getMoviesPageOne()
    }

    private fun startLoadingMovies() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            progressBarBase.postValue(true)
            isLoading = true

            moviesUseCase.startLoadingMovies(typeSort, page, lang)

            _movies.postValue(moviesUseCase.getAllMoviesDB())

            progressBarBase.postValue(false)
            isLoading = false
            isErrorInternet = false
        }
    }

    private suspend fun setErrorInternetNotification(error: Throwable) {
        errorInternetNotificationBase.emit(error)
        progressBarBase.postValue(false)
        isErrorInternet = true
    }

    fun getQueryMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            progressBarBase.postValue(true)
            isLoading = true

            moviesUseCase.getQueryMoviesApi(query)

            _movies.postValue(moviesUseCase.getAllMoviesDB())

            progressBarBase.postValue(false)
            isErrorInternet = false
        }
    }

    fun getMoviesPageOne() {
        page = 1
        startLoadingMovies()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}