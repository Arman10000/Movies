package android.example.movies.presentation.di.component

import android.app.Application
import android.example.movies.presentation.di.annotation.ApplicationScope
import android.example.movies.presentation.di.module.DataModule
import android.example.movies.presentation.di.module.DomainModule
import android.example.movies.presentation.di.module.ViewModelModule
import android.example.movies.presentation.screen.DetailMovie
import android.example.movies.presentation.screen.FavouriteMovies
import android.example.movies.presentation.screen.Movies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: Movies)
    fun inject(fragment: DetailMovie)
    fun inject(fragment: FavouriteMovies)

    @Component.Factory
    interface AppComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent

    }

}