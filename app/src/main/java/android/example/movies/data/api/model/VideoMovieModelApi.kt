package android.example.movies.data.api.model

import android.example.movies.domain.item.VideoMovieItem
import com.google.gson.annotations.SerializedName

data class VideoMovieModelApi(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val nameVideo: String
) {

    fun toVideoMovieItem() = VideoMovieItem(
        key = this.key,
        nameVideo = this.nameVideo
    )

}