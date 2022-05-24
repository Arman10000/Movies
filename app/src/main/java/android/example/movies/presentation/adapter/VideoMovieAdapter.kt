package android.example.movies.presentation.adapter

import android.example.data.api.MoviesApi
import android.example.domain.item.VideoMovieItem
import android.example.movies.databinding.VideoItemBinding
import android.example.movies.presentation.adapter.callback.VideoMovieItemDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class VideoMovieAdapter(
    private val openVideo: (url: String) -> Unit
) : ListAdapter<VideoMovieItem, VideoMovieAdapter.VideoViewHolder>(VideoMovieItemDiffCallback()) {

    class VideoViewHolder(
        private val binding: VideoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            videoMovieItem: VideoMovieItem,
            openVideo: (url: String) -> Unit
        ) {
            binding.ivPlay.setOnClickListener {
                openVideo(MoviesApi.BASE_YOUTUBE_URL + videoMovieItem.key)
            }
            binding.tvNameVideo.text = videoMovieItem.nameVideo
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VideoItemBinding.inflate(layoutInflater, parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            openVideo
        )
    }

}