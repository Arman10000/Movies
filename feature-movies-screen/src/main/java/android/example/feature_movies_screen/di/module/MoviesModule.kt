package android.example.feature_movies_screen.di.module

import android.example.core.api.RetrofitBuilder
import android.example.core.db.MoviesDao
import android.example.core.di.annotation.ApplicationScope
import android.example.feature_movies_screen.data.api.MoviesApi
import android.example.feature_movies_screen.data.mapper.MoviesMapper
import android.example.feature_movies_screen.data.repository.MoviesRepository
import android.example.feature_movies_screen.data.repository.MoviesRepositoryImpl
import android.example.feature_movies_screen.domain.MoviesUseCase
import android.example.feature_movies_screen.domain.MoviesUseCaseImpl
import android.example.feature_movies_screen.presentation.viewModel.MoviesViewModel
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @Provides
    fun provideMoviesApi(retrofitBuilder: RetrofitBuilder) =
        retrofitBuilder.create(MoviesApi::class.java)

    @Provides
    fun provideMoviesMapper() = MoviesMapper()

    @Provides
    fun provideMoviesRepository(
        moviesApi: MoviesApi,
        moviesDao: MoviesDao,
        moviesMapper: MoviesMapper
    ): MoviesRepository = MoviesRepositoryImpl(
        moviesApi,
        moviesDao,
        moviesMapper
    )

    @Provides
    fun provideMoviesUseCase(
        moviesRepository: MoviesRepository
    ): MoviesUseCase = MoviesUseCaseImpl(
        moviesRepository
    )

    @ApplicationScope
    @Provides
    fun provideMoviesViewModel(
        moviesUseCase: MoviesUseCase
    ) = MoviesViewModel(moviesUseCase)
}