package android.example.movies.presentation.viewModel

import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.useCase.MovieUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavouriteMoviesViewModel(
    movieUseCase: MovieUseCase
) : ViewModel() {
    val favouriteMovies: LiveData<List<FavouriteMovieItem>> = movieUseCase.getAllFavouriteMoviesDB()
}