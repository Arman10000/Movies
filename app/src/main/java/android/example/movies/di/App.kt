package android.example.movies.di

import android.app.Application
import android.example.feature_details_movie_screen.di.component.DaggerDetailsMovieComponent
import android.example.feature_details_movie_screen.di.component.DetailsMovieComponent
import android.example.feature_details_movie_screen.di.provider.DetailsMovieComponentProvider
import android.example.feature_favourite_movies_screen.di.component.DaggerFavouriteMoviesComponent
import android.example.feature_favourite_movies_screen.di.component.FavouriteMoviesComponent
import android.example.feature_favourite_movies_screen.di.provider.FavouriteMoviesComponentProvider
import android.example.feature_movies_screen.di.component.DaggerMoviesComponent
import android.example.feature_movies_screen.di.component.MoviesComponent
import android.example.feature_movies_screen.di.provider.MoviesComponentProvider

class App : Application(),
    DetailsMovieComponentProvider,
    MoviesComponentProvider,
    FavouriteMoviesComponentProvider {

    private var moviesComponent: MoviesComponent? = null
    private var detailsMovieComponent: DetailsMovieComponent? = null
    private var favouriteMoviesComponent: FavouriteMoviesComponent? = null

    override fun getMoviesComponent(): MoviesComponent {
        moviesComponent?.let { return it }
        val instant = DaggerMoviesComponent.factory().create(this)
        moviesComponent = instant
        return instant
    }


    override fun getDetailsMovieComponent(): DetailsMovieComponent {
        detailsMovieComponent?.let { return it }
        val instant = DaggerDetailsMovieComponent.factory().create(this)
        detailsMovieComponent = instant
        return instant
    }

    override fun getFavouriteMoviesComponent(): FavouriteMoviesComponent {
        favouriteMoviesComponent?.let { return it }
        val instant = DaggerFavouriteMoviesComponent.factory().create(this)
        favouriteMoviesComponent = instant
        return instant
    }
}