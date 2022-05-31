package android.example.feature_details_movie_screen.di.module

import android.example.core.api.RetrofitBuilder
import android.example.core.db.MoviesDao
import android.example.core.di.annotation.ApplicationScope
import android.example.feature_details_movie_screen.data.api.DetailsMovieApi
import android.example.feature_details_movie_screen.data.mapper.CommentsMovieMapper
import android.example.feature_details_movie_screen.data.mapper.DetailsMovieMapper
import android.example.feature_details_movie_screen.data.mapper.VideosMovieMapper
import android.example.feature_details_movie_screen.data.repository.DetailsMovieRepository
import android.example.feature_details_movie_screen.data.repository.DetailsMovieRepositoryImpl
import android.example.feature_details_movie_screen.domain.DetailsMovieUseCase
import android.example.feature_details_movie_screen.domain.DetailsMovieUseCaseImpl
import android.example.feature_details_movie_screen.presentation.viewModel.DetailsMovieViewModel
import dagger.Module
import dagger.Provides

@Module
class DetailsMovieModule {

    @Provides
    fun provideDetailsMovieApi(retrofitBuilder: RetrofitBuilder) =
        retrofitBuilder.create(DetailsMovieApi::class.java)

    @Provides
    fun provideDetailsMovieMapper() = DetailsMovieMapper()

    @Provides
    fun provideCommentsMovieMapper() = CommentsMovieMapper()

    @Provides
    fun provideVideosMovieMapper() = VideosMovieMapper()

    @Provides
    fun provideDetailsMovieRepository(
        detailsMovieApi: DetailsMovieApi,
        moviesDao: MoviesDao,
        detailsMovieMapper: DetailsMovieMapper,
        commentsMovieMapper: CommentsMovieMapper,
        videosMovieMapper: VideosMovieMapper
    ): DetailsMovieRepository = DetailsMovieRepositoryImpl(
        detailsMovieApi,
        moviesDao,
        detailsMovieMapper,
        commentsMovieMapper,
        videosMovieMapper
    )

    @Provides
    fun provideDetailsMovieUseCase(
        detailsMovieRepository: DetailsMovieRepository
    ): DetailsMovieUseCase = DetailsMovieUseCaseImpl(
        detailsMovieRepository
    )

    @ApplicationScope
    @Provides
    fun provideDetailsMovieViewModel(
        detailsMovieUseCase: DetailsMovieUseCase
    ) = DetailsMovieViewModel(detailsMovieUseCase)
}