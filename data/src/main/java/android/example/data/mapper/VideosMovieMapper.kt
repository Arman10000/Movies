package android.example.data.mapper

import android.example.data.api.model.VideoMovieModelApi
import android.example.data.api.model.VideosMovieList
import android.example.data.db.model.VideoMovieModelDB
import android.example.domain.item.VideoMovieItem
import retrofit2.Response

class VideosMovieMapper {

    private fun mapVideoMovieModelDBToEntity(
        videoMovieModelDB: VideoMovieModelDB
    ) = VideoMovieItem(
        videoMovieModelDB.key,
        videoMovieModelDB.nameVideo
    )

    private fun mapEntityToVideoMovieModelDB(
        videoMovieItem: VideoMovieItem,
        movieId: Int
    ) = VideoMovieModelDB(
        movieId = movieId,
        key = videoMovieItem.key,
        nameVideo = videoMovieItem.nameVideo
    )

    private fun mapVideoMovieModelApiToEntity(
        videoMovieModelApi: VideoMovieModelApi
    ) = VideoMovieItem(
        videoMovieModelApi.key,
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