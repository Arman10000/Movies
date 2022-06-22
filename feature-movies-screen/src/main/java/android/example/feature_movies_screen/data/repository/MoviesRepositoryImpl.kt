package android.example.feature_movies_screen.data.repository

import android.example.core.db.MoviesDao
import android.example.core.item.MovieItem
import android.example.feature_movies_screen.data.api.MoviesApi
import android.example.feature_movies_screen.data.mapper.MoviesMapper

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val moviesMapper: MoviesMapper
) : MoviesRepository {

    override fun getMoviesApi(
        sortBy: String,
        page: Int,
        lang: String
    ): List<MovieItem> = moviesMapper.mapListMovieModelApiToListEntity(
        moviesApi.getMovies(sortBy, page, lang).execute()
    )

    override fun getQueryMoviesApi(
        query: String
    ): List<MovieItem> = moviesMapper.mapListMovieModelApiToListEntity(
        moviesApi.getQueryMovies(query).execute()
    )

    override suspend fun getAllMoviesDB(): List<MovieItem> =
        moviesMapper.mapListMovieModelDBToListEntity(list = moviesDao.getAllMovies())

    override suspend fun addMoviesDB(movies: List<MovieItem>) {
        moviesDao.addMovies(movies = moviesMapper.mapListEntityToListMovieModelDB(movies))
    }

    override suspend fun clearMoviesDB() {
        moviesDao.clearMovies()
    }
}