package android.example.movies.presentation.di.module

import android.example.movies.domain.repository.MovieRepository
import android.example.movies.domain.useCase.MovieUseCase
import android.example.movies.domain.useCase.MovieUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideUseCase(
        movieRepository: MovieRepository
    ): MovieUseCase {
        return MovieUseCaseImpl(
            movieRepository = movieRepository
        )
    }

}