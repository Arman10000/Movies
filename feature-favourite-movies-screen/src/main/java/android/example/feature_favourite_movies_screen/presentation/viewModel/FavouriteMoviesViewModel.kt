package android.example.feature_favourite_movies_screen.presentation.viewModel

import android.example.core.viewModel.BaseViewModel
import android.example.feature_favourite_movies_screen.domain.useCase.FavouriteMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteMoviesViewModel(
    private val favouriteMoviesUseCase: FavouriteMoviesUseCase
) : BaseViewModel() {

    fun getAllFavouriteMoviesDB() {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteMoviesBase.postValue(favouriteMoviesUseCase.getAllFavouriteMoviesDB())
        }
    }
}