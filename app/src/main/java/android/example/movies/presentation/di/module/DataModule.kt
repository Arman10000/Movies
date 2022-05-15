package android.example.movies.presentation.di.module

import android.app.Application
import android.example.movies.data.api.MoviesApi
import android.example.movies.data.db.MoviesDao
import android.example.movies.data.db.MoviesDatabase
import android.example.movies.data.mapper.CommentsMovieMapper
import android.example.movies.data.mapper.FavouriteMovieMapper
import android.example.movies.data.mapper.MovieMapper
import android.example.movies.data.mapper.VideosMovieMapper
import android.example.movies.data.repository.MovieRepositoryImpl
import android.example.movies.domain.repository.MovieRepository
import android.example.movies.presentation.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideMovieDatabase(application: Application) =
        MoviesDatabase.getInstance(application = application)

    @Provides
    fun provideMovieDao(movieDatabase: MoviesDatabase) =
        movieDatabase.movieDao()

    @ApplicationScope
    @Provides
    fun provideMoviesApi(): MoviesApi =
        Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)

    @Provides
    fun provideCommentsMovieMapper() =
        CommentsMovieMapper()

    @Provides
    fun provideVideosMovieMapper() =
        VideosMovieMapper()

    @Provides
    fun provideMovieMapper() =
        MovieMapper()

    @Provides
    fun provideFavouriteMovieMapper() =
        FavouriteMovieMapper()

    @Provides
    fun provideMovieRepository(
        moviesApi: MoviesApi,
        moviesDao: MoviesDao,
        movieMapper: MovieMapper,
        FavouriteMovieMapper: FavouriteMovieMapper,
        commentsMovieMapper: CommentsMovieMapper,
        videosMovieMapper: VideosMovieMapper
    ): MovieRepository {
        return MovieRepositoryImpl(
            moviesApi = moviesApi,
            moviesDao = moviesDao,
            movieMapper = movieMapper,
            FavouriteMovieMapper = FavouriteMovieMapper,
            commentsMovieMapper = commentsMovieMapper,
            videosMovieMapper = videosMovieMapper
        )
    }

}