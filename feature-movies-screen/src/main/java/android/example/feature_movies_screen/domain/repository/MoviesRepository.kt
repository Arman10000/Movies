package android.example.feature_movies_screen.domain.repository

import android.example.core.item.MovieItem

interface MoviesRepository {

    fun getMoviesApi(sortBy: String, page: Int, lang: String): List<MovieItem>

    fun getQueryMoviesApi(query: String, lang: String): List<MovieItem>

    suspend fun getAllMoviesDB(): List<MovieItem>

    suspend fun addMoviesDB(movies: List<MovieItem>)

    suspend fun clearMoviesDB()
}