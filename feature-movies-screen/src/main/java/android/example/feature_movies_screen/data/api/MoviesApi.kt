package android.example.feature_movies_screen.data.api

import android.example.core.const.API_KEY
import android.example.core.const.PARAMS_API_KEY
import android.example.core.const.PARAMS_LANGUAGE
import android.example.feature_movies_screen.data.api.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    companion object {
        private const val PARAMS_SORT_BY = "sort_by"
        private const val PARAMS_PAGE = "page"
        private const val PARAMS_MIN_VOTE_COUNT = "vote_count.gte"
        private const val VALUE_MIN_VOTE_COUNT = "1000"
        private const val BASE_URL_MOVIES = "discover/movie"
        private const val BASE_URL_QUERY_MOVIES = "search/movie"
        private const val PARAMS_QUERY = "query"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w"
        const val BIG_POSTER_SIZE = "780"
        const val SORT_BY_POPULARITY = "popularity.desc"
        const val SORT_BY_TOP_RATED = "vote_average.desc"
    }

    @GET("$BASE_URL_MOVIES?$PARAMS_API_KEY=$API_KEY&$PARAMS_MIN_VOTE_COUNT=$VALUE_MIN_VOTE_COUNT")
    fun getMovies(
        @Query(PARAMS_SORT_BY) sortBy: String,
        @Query(PARAMS_PAGE) page: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<MovieList>

    @GET("$BASE_URL_QUERY_MOVIES?$PARAMS_API_KEY=$API_KEY")
    fun getQueryMovies(
        @Query(PARAMS_QUERY) query: String
    ): Call<MovieList>
}