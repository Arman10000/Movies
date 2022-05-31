package android.example.feature_details_movie_screen.presentation.adapter.callback

import android.example.feature_details_movie_screen.domain.item.VideoMovieItem
import androidx.recyclerview.widget.DiffUtil

class VideoMovieItemDiffCallback : DiffUtil.ItemCallback<VideoMovieItem>() {

    override fun areItemsTheSame(oldItem: VideoMovieItem, newItem: VideoMovieItem): Boolean {
        return oldItem.nameVideo == newItem.nameVideo
    }

    override fun areContentsTheSame(oldItem: VideoMovieItem, newItem: VideoMovieItem): Boolean {
        return oldItem == newItem
    }

}