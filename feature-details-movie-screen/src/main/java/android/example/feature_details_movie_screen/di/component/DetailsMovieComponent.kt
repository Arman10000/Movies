package android.example.feature_details_movie_screen.di.component

import android.app.Application
import android.example.core.di.annotation.ApplicationScope
import android.example.core.di.module.CoreModule
import android.example.feature_details_movie_screen.di.module.DetailsMovieModule
import android.example.feature_details_movie_screen.presentation.screen.DetailsMovie
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DetailsMovieModule::class, CoreModule::class])
interface DetailsMovieComponent {

    fun inject(movies: DetailsMovie)

    @Component.Factory
    interface ComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): DetailsMovieComponent
    }
}