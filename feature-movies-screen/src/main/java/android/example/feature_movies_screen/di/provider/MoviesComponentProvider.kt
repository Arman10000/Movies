package android.example.feature_movies_screen.di.provider

import android.example.feature_movies_screen.di.component.MoviesComponent

interface MoviesComponentProvider {
    fun getMoviesComponent(): MoviesComponent
}