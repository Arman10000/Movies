package android.example.core.di.module

import android.example.core.api.RetrofitBuilder
import android.example.core.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @ApplicationScope
    @Provides
    fun provideRetrofit() = RetrofitBuilder()
}