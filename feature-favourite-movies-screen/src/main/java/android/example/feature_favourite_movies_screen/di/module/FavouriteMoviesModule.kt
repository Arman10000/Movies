package android.example.feature_favourite_movies_screen.di.module

import android.example.core.db.MoviesDao
import android.example.core.di.annotation.ApplicationScope
import android.example.feature_favourite_movies_screen.data.mapper.FavouriteMoviesMapper
import android.example.feature_favourite_movies_screen.domain.repository.FavouriteMoviesRepository
import android.example.feature_favourite_movies_screen.data.repository.FavouriteMoviesRepositoryImpl
import android.example.feature_favourite_movies_screen.domain.useCase.FavouriteMoviesUseCase
import android.example.feature_favourite_movies_screen.domain.useCase.FavouriteMoviesUseCaseImpl
import android.example.feature_favourite_movies_screen.presentation.viewModel.FavouriteMoviesViewModel
import dagger.Module
import dagger.Provides

@Module
class FavouriteMoviesModule {

    @Provides
    fun provideFavouriteMoviesMapper() = FavouriteMoviesMapper()

    @Provides
    fun provideFavouriteMoviesRepository(
        moviesDao: MoviesDao,
        favouriteMoviesMapper: FavouriteMoviesMapper
    ): FavouriteMoviesRepository = FavouriteMoviesRepositoryImpl(
        moviesDao,
        favouriteMoviesMapper
    )

    @Provides
    fun provideFavouriteMoviesUseCase(
        favouriteMoviesRepository: FavouriteMoviesRepository
    ): FavouriteMoviesUseCase = FavouriteMoviesUseCaseImpl(
        favouriteMoviesRepository
    )

    @ApplicationScope
    @Provides
    fun provideFavouriteMoviesViewModel(
        favouriteMoviesUseCase: FavouriteMoviesUseCase
    ) = FavouriteMoviesViewModel(favouriteMoviesUseCase)
}