package android.example.feature_details_movie_screen.data.api

import android.example.core.const.API_KEY
import android.example.core.const.PARAMS_API_KEY
import android.example.core.const.PARAMS_LANGUAGE
import android.example.feature_details_movie_screen.data.api.model.CommentsMovieList
import android.example.feature_details_movie_screen.data.api.model.VideosMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsMovieApi {
    companion object {
        private const val BASE_URL_COMMENTS = "movie/{movie_id}/reviews"
        private const val BASE_URL_VIDEOS = "movie/{movie_id}/videos"
        private const val PARAMS_MOVIE_ID = "movie_id"
    }

    @GET("${BASE_URL_VIDEOS}?${PARAMS_API_KEY}=${API_KEY}")
    fun getVideosMovie(
        @Path(PARAMS_MOVIE_ID) movieId: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<VideosMovieList>

    @GET("${BASE_URL_COMMENTS}?${PARAMS_API_KEY}=${API_KEY}")
    fun getCommentsMovie(
        @Path(PARAMS_MOVIE_ID) movieId: Int,
        @Query(PARAMS_LANGUAGE) lang: String
    ): Call<CommentsMovieList>
}