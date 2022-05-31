package android.example.core.adapter.callback

import android.example.core.item.MovieItem
import androidx.recyclerview.widget.DiffUtil

class MovieItemDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }

}