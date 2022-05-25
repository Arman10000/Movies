package android.example.movies.presentation.viewModel

import android.example.domain.item.MovieItem
import android.example.domain.useCase.MovieUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _favouriteMovies: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val favouriteMovies: LiveData<List<MovieItem>> = _favouriteMovies

    fun getAllFavouriteMoviesDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteMovies.postValue(movieUseCase.getAllFavouriteMoviesDB())
        }
    }
}