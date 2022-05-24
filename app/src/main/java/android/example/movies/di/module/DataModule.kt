package android.example.movies.di.module

import android.app.Application
import android.example.data.api.MoviesApi
import android.example.data.db.MoviesDao
import android.example.data.db.MoviesDatabase
import android.example.data.mapper.CommentsMovieMapper
import android.example.data.mapper.MovieMapper
import android.example.data.mapper.VideosMovieMapper
import android.example.data.repository.MovieRepositoryImpl
import android.example.domain.repository.MovieRepository
import android.example.movies.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideMovieDatabase(application: Application) = MoviesDatabase.getInstance(application)

    @Provides
    fun provideMovieDao(movieDatabase: MoviesDatabase) = movieDatabase.movieDao()

    @ApplicationScope
    @Provides
    fun provideMoviesApi(): MoviesApi = Retrofit.Builder()
        .baseUrl(MoviesApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MoviesApi::class.java)

    @Provides
    fun provideCommentsMovieMapper() = CommentsMovieMapper()

    @Provides
    fun provideVideosMovieMapper() = VideosMovieMapper()

    @Provides
    fun provideMovieMapper() = MovieMapper()

    @Provides
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        moviesDao: MoviesDao,
        movieMapper: MovieMapper,
        commentsMovieMapper: CommentsMovieMapper,
        videosMovieMapper: VideosMovieMapper
    ): MovieRepository = MovieRepositoryImpl(
        moviesApi,
        moviesDao,
        movieMapper,
        commentsMovieMapper,
        videosMovieMapper
    )

}