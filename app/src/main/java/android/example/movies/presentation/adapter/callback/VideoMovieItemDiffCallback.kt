package android.example.movies.presentation.adapter.callback

import android.example.domain.item.VideoMovieItem
import androidx.recyclerview.widget.DiffUtil

class VideoMovieItemDiffCallback : DiffUtil.ItemCallback<VideoMovieItem>() {

    override fun areItemsTheSame(oldItem: VideoMovieItem, newItem: VideoMovieItem): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: VideoMovieItem, newItem: VideoMovieItem): Boolean {
        return oldItem == newItem
    }

}