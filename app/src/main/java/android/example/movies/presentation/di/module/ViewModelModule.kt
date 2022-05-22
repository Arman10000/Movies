package android.example.movies.presentation.di.module

import android.example.movies.domain.useCase.MovieUseCase
import android.example.movies.presentation.di.annotation.ApplicationScope
import android.example.movies.presentation.di.annotation.ViewModelKey
import android.example.movies.presentation.viewModel.MoviesViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    @Provides
    fun provideMovieViewModel(
        movieUseCase: MovieUseCase
    ): ViewModel = MoviesViewModel(
        movieUseCase = movieUseCase
    )

    @ApplicationScope
    @Provides
    fun provideViewModelFactory(
        viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
    ) = ViewModelFactory(viewModelProviders)

}