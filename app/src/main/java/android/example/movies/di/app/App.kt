package android.example.movies.di.app

import android.app.Application
import android.example.movies.di.component.DaggerAppComponent


class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}