package android.example.feature_details_movie_screen.data.mapper

import android.example.core.db.model.VideoMovieModelDB
import android.example.feature_details_movie_screen.data.api.DetailsMovieApi.Companion.BASE_YOUTUBE_URL
import android.example.feature_details_movie_screen.data.api.model.VideoMovieModelApi
import android.example.feature_details_movie_screen.data.api.model.VideosMovieList
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem
import retrofit2.Response

class VideosMovieMapper {

    private fun mapVideoMovieModelDBToEntity(
        videoMovieModelDB: VideoMovieModelDB
    ) = VideoMovieItem(
        videoMovieModelDB.urlVideo,
        videoMovieModelDB.nameVideo
    )

    private fun mapEntityToVideoMovieModelDB(
        videoMovieItem: VideoMovieItem,
        movieId: Int
    ) = VideoMovieModelDB(
        movieId = movieId,
        urlVideo = videoMovieItem.urlVideo,
        nameVideo = videoMovieItem.nameVideo
    )

    private fun mapVideoMovieModelApiToEntity(
        videoMovieModelApi: VideoMovieModelApi
    ) = VideoMovieItem(
        BASE_YOUTUBE_URL + videoMovieModelApi.key,
        videoMovieModelApi.nameVideo
    )

    fun mapListVideoMovieModelDBToListEntity(
        list: List<VideoMovieModelDB>
    ) = list.map {
        mapVideoMovieModelDBToEntity(videoMovieModelDB = it)
    }

    fun mapListEntityToListVideoMovieModelDB(
        list: List<VideoMovieItem>,
        movieId: Int
    ) = list.map {
        mapEntityToVideoMovieModelDB(
            videoMovieItem = it,
            movieId
        )
    }

    fun mapListVideoMovieModelApiToListEntity(
        response: Response<VideosMovieList>
    ): List<VideoMovieItem> {
        response.body()?.let {
            return it.videosMovieList.map { videoMovieModelApi ->
                mapVideoMovieModelApiToEntity(videoMovieModelApi)
            }
        }

        return listOf()
    }

}