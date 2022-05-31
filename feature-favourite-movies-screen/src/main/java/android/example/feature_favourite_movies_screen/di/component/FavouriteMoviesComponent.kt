package android.example.feature_favourite_movies_screen.di.component

import android.app.Application
import android.example.core.di.annotation.ApplicationScope
import android.example.core.di.module.CoreModule
import android.example.feature_favourite_movies_screen.di.module.FavouriteMoviesModule
import android.example.feature_favourite_movies_screen.presentation.screen.FavouriteMovies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [FavouriteMoviesModule::class, CoreModule::class])
interface FavouriteMoviesComponent {

    fun inject(favouriteMovies: FavouriteMovies)

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): FavouriteMoviesComponent
    }
}