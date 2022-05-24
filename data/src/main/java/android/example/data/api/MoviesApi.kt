package android.example.data.api

import android.example.data.api.model.CommentsMovieList
import android.example.data.api.model.MovieList
import android.example.data.api.model.VideosMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    companion object {
        private const val API_KEY = "84f0f3a7bd08769b95016fc25a80a3ef"
        private const val PARAMS_API_KEY = "api_key"
        private const val PARAMS_LANGUAGE = "language"
        private const val PARAMS_SORT_BY = "sort_by"
        private const val PARAMS_PAGE = "page"
        private const val PARAMS_MIN_VOTE_COUNT = "vote_count.gte"
        private const val MIN_VOTE_COUNT_VALUE = "1000"
        private const val BASE_URL_MOVIES = "discover/movie"
        private const val BASE_URL_VIDEOS = "movie/{movie_id}/videos"
        private const val BASE_URL_COMMENTS = "movie/{movie_id}/reviews"
        private const val MOVIE_ID = "movie_id"

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w"
        const val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="
        const val SMALL_POSTER_SIZE = "185"
        const val BIG_POSTER_SIZE = "780"
        const val SORT_BY_POPULARITY = "popularity.desc"
        const val SORT_BY_TOP_RATED = "vote_average.desc"
    }

    @GET("$BASE_URL_MOVIES?$PARAMS_API_KEY=$API_KEY&$PARAMS_MIN_VOTE_COUNT=$MIN_VOTE_COUNT_VALUE")
    fun getMovies(
        @Query(PARAMS_SORT_BY) sortBy: String,
        @Query(PARAMS_PAGE) page: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<MovieList>

    @GET("$BASE_URL_VIDEOS?$PARAMS_API_KEY=$API_KEY")
    fun getVideosMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<VideosMovieList>

    @GET("$BASE_URL_COMMENTS?$PARAMS_API_KEY=$API_KEY")
    fun getCommentsMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<CommentsMovieList>
}