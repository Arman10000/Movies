package android.example.feature_movies_screen.domain.useCase

import android.example.core.item.MovieItem
import android.example.feature_movies_screen.domain.repository.MoviesRepository

class MoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository
) : MoviesUseCase {

    override suspend fun getAllMoviesDB(): List<MovieItem> =
        moviesRepository.getAllMoviesDB()

    override suspend fun startLoadingMovies(
        sortBy: String,
        page: Int,
        lang: String
    ) {
        val movies = moviesRepository.getMoviesApi(sortBy, page, lang)

        if (movies.isNotEmpty()) {
            if (page == 1) moviesRepository.clearMoviesDB()
            moviesRepository.addMoviesDB(movies)
        }
    }

    override suspend fun getQueryMoviesApi(query: String, lang: String) {
        val moviesQuery = moviesRepository.getQueryMoviesApi(query, lang)

        moviesRepository.clearMoviesDB()
        moviesRepository.addMoviesDB(moviesQuery)
    }
}