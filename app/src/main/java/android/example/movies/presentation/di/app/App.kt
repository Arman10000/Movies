package android.example.movies.presentation.di.app

import android.app.Application
import android.example.movies.presentation.di.component.DaggerAppComponent


class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}