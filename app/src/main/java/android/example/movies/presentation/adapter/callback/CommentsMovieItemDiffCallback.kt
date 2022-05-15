package android.example.movies.presentation.adapter.callback

import android.example.movies.domain.item.CommentMovieItem
import androidx.recyclerview.widget.DiffUtil

class CommentsMovieItemDiffCallback : DiffUtil.ItemCallback<CommentMovieItem>() {

    override fun areItemsTheSame(oldItem: CommentMovieItem, newItem: CommentMovieItem): Boolean {
        return oldItem.nameAuthor == newItem.nameAuthor
    }

    override fun areContentsTheSame(oldItem: CommentMovieItem, newItem: CommentMovieItem): Boolean {
        return oldItem == newItem
    }
}