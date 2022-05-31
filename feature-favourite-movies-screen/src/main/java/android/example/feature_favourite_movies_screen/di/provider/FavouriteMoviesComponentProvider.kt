package android.example.feature_favourite_movies_screen.di.provider

import android.example.feature_favourite_movies_screen.di.component.FavouriteMoviesComponent

interface FavouriteMoviesComponentProvider {
    fun getFavouriteMoviesComponent(): FavouriteMoviesComponent
}