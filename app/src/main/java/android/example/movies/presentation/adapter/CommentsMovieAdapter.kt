package android.example.movies.presentation.adapter

import android.example.movies.databinding.CommentsItemBinding
import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.presentation.adapter.callback.CommentsMovieItemDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CommentsMovieAdapter :
    ListAdapter<CommentMovieItem, CommentsMovieAdapter.CommentsViewHolder>(CommentsMovieItemDiffCallback()) {

    class CommentsViewHolder(
        private val binding: CommentsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            commentsMovieItem: CommentMovieItem
        ) {
            binding.tvNameAuthor.text = commentsMovieItem.nameAuthor
            binding.tvComment.text = commentsMovieItem.comment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentsItemBinding.inflate(layoutInflater, parent, false)
        return CommentsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}