package android.example.feature_favourite_movies_screen.di.module

import android.app.Application
import android.example.core.db.Database
import android.example.core.di.annotation.ApplicationScope
import android.example.feature_favourite_movies_screen.data.db.FavouriteMoviesDao
import android.example.feature_favourite_movies_screen.data.db.FavouriteMoviesDatabase
import android.example.feature_favourite_movies_screen.data.mapper.FavouriteMoviesMapper
import android.example.feature_favourite_movies_screen.data.repository.FavouriteMoviesRepository
import android.example.feature_favourite_movies_screen.data.repository.FavouriteMoviesRepositoryImpl
import android.example.feature_favourite_movies_screen.domain.FavouriteMoviesUseCase
import android.example.feature_favourite_movies_screen.domain.FavouriteMoviesUseCaseImpl
import android.example.feature_favourite_movies_screen.presentation.viewModel.FavouriteMoviesViewModel
import dagger.Module
import dagger.Provides

@Module
class FavouriteMoviesModule {

    @ApplicationScope
    @Provides
    fun provideFavouriteMoviesDatabase(application: Application) = Database.getInstance<FavouriteMoviesDatabase>(application)

    @Provides
    fun provideFavouriteMoviesDao(favouriteMoviesDatabase: FavouriteMoviesDatabase) = favouriteMoviesDatabase.getFavouriteMoviesDao()

    @Provides
    fun provideFavouriteMoviesMapper() = FavouriteMoviesMapper()

    @Provides
    fun provideFavouriteMoviesRepository(
        favouriteMoviesDao: FavouriteMoviesDao,
        favouriteMoviesMapper: FavouriteMoviesMapper
    ): FavouriteMoviesRepository = FavouriteMoviesRepositoryImpl(
        favouriteMoviesDao,
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