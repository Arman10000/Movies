package android.example.movies.di.module

import android.example.domain.repository.MovieRepository
import android.example.domain.useCase.MovieUseCase
import android.example.domain.useCase.MovieUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideUseCase(
        movieRepository: MovieRepository
    ): MovieUseCase = MovieUseCaseImpl(
        movieRepository
    )
}