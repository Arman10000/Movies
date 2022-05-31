package android.example.core.di.module

import android.app.Application
import android.example.core.api.RetrofitBuilder
import android.example.core.db.MoviesDatabase
import android.example.core.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @ApplicationScope
    @Provides
    fun provideRetrofit() = RetrofitBuilder()

    @ApplicationScope
    @Provides
    fun provideMovieDatabase(application: Application) = MoviesDatabase.getInstance(application)

    @ApplicationScope
    @Provides
    fun provideMovieDao(movieDatabase: MoviesDatabase) = movieDatabase.movieDao()
}