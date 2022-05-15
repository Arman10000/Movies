package android.example.movies.data.mapper

import android.example.movies.data.db.model.VideoMovieModelDB
import android.example.movies.domain.item.VideoMovieItem

class VideosMovieMapper {

    private fun mapVideoMovieModelDBToEntity(
        videoMovieModelDB: VideoMovieModelDB
    ) = VideoMovieItem(
        key = videoMovieModelDB.key,
        nameVideo = videoMovieModelDB.nameVideo
    )

    private fun mapEntityToVideoMovieModelDB(
        videoMovieItem: VideoMovieItem,
        movieId: Int
    ) = VideoMovieModelDB(
        movieId = movieId,
        key = videoMovieItem.key,
        nameVideo = videoMovieItem.nameVideo
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
            movieId = movieId
        )
    }

}