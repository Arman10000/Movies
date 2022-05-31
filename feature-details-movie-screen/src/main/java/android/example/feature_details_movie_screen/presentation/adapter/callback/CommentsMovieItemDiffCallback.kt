package android.example.feature_details_movie_screen.presentation.adapter.callback

import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import androidx.recyclerview.widget.DiffUtil

class CommentsMovieItemDiffCallback : DiffUtil.ItemCallback<CommentMovieItem>() {

    override fun areItemsTheSame(oldItem: CommentMovieItem, newItem: CommentMovieItem): Boolean {
        return oldItem.nameAuthor == newItem.nameAuthor
    }

    override fun areContentsTheSame(oldItem: CommentMovieItem, newItem: CommentMovieItem): Boolean {
        return oldItem == newItem
    }
}