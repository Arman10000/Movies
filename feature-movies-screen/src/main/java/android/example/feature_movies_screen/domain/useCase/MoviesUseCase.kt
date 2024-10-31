package android.example.feature_movies_screen.domain.useCase

import android.example.core.item.MovieItem

interface MoviesUseCase {

    suspend fun getAllMoviesDB(): List<MovieItem>

    suspend fun startLoadingMovies(sortBy: String, page: Int, lang: String)

    suspend fun getQueryMoviesApi(query: String, lang: String)
}