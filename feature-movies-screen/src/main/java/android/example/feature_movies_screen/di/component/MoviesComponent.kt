package android.example.feature_movies_screen.di.component

import android.app.Application
import android.example.core.di.annotation.ApplicationScope
import android.example.core.di.module.CoreModule
import android.example.feature_movies_screen.di.module.MoviesModule
import android.example.feature_movies_screen.presentation.screen.Movies
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [MoviesModule::class, CoreModule::class])
interface MoviesComponent {

    fun inject(movies: Movies)

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): MoviesComponent
    }

}