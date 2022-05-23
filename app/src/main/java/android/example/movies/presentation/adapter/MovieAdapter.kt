package android.example.movies.presentation.adapter

import android.example.movies.databinding.MovieItemBinding
import android.example.movies.domain.item.MovieItem
import android.example.movies.presentation.adapter.callback.MovieItemDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val openDetailsMovie: (movieItem: MovieItem) -> Unit
) : ListAdapter<MovieItem, MovieAdapter.MovieViewHolder>(MovieItemDiffCallback()) {

    class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movieItem: MovieItem,
            openDetailsMovie: (movieItem: MovieItem) -> Unit
        ) {
            Picasso.get().load(movieItem.smallPoster).into(binding.ivSmallPoster)
            binding.ivSmallPoster.setOnClickListener {
                openDetailsMovie(movieItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(
            movieItem = getItem(position),
            openDetailsMovie = openDetailsMovie
        )
    }

}