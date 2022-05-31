package android.example.feature_details_movie_screen.presentation.screen

import android.content.Context
import android.content.Intent
import android.example.core.item.MovieItem
import android.example.core.navigation.getMovieIdArgument
import android.example.core.navigation.getOpenByArgument
import android.example.feature_details_movie_screen.R
import android.example.feature_details_movie_screen.databinding.DetailsMovieBinding
import android.example.feature_details_movie_screen.di.provider.DetailsMovieComponentProvider
import android.example.feature_details_movie_screen.presentation.adapter.CommentsMovieAdapter
import android.example.feature_details_movie_screen.presentation.adapter.VideoMovieAdapter
import android.example.feature_details_movie_screen.presentation.const.KEY_SAVE_FRAGMENT_STATE
import android.example.feature_details_movie_screen.presentation.viewModel.DetailsMovieViewModel
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject

class DetailsMovie : Fragment(R.layout.details_movie), View.OnClickListener {

    @Inject
    lateinit var viewModel: DetailsMovieViewModel
    private var _binding: DetailsMovieBinding? = null
    private var _movieItem: MovieItem? = null
    private var _favouriteMovies: ArrayList<MovieItem>? = null
    private val binding get() = _binding!!
    private val movieItem get() = _movieItem!!
    private val favouriteMovies: ArrayList<MovieItem> get() = _favouriteMovies!!
    private var firstStartDetailsMovie: Boolean = true

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _movieItem = null
        _favouriteMovies = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SAVE_FRAGMENT_STATE, firstStartDetailsMovie)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            firstStartDetailsMovie = it.getBoolean(KEY_SAVE_FRAGMENT_STATE)
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as DetailsMovieComponentProvider)
            .getDetailsMovieComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(0, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = DetailsMovieBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        val movieId = getMovieIdArgument()
        val openBy = getOpenByArgument()

        if (firstStartDetailsMovie && movieId != null && openBy != null) {
            firstStartDetailsMovie = false
            viewModel.loadingVideosCommentsMovie(movieId, openBy)
        }

        binding.ivAddToFavourite.setOnClickListener(this)
        binding.movieInfo.rvVideos.layoutManager = LinearLayoutManager(requireContext())
        binding.movieInfo.rvComments.layoutManager = LinearLayoutManager(requireContext())

        val commentsMovieAdapter = CommentsMovieAdapter()
        val videoMovieAdapter = VideoMovieAdapter(
            openVideo = {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                } catch (t: Throwable) {
                    Snackbar
                        .make(binding.root, R.string.not_action_view, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        )

        binding.movieInfo.rvComments.adapter = commentsMovieAdapter
        binding.movieInfo.rvVideos.adapter = videoMovieAdapter

        viewModel.movie.observe(viewLifecycleOwner) { movieItem ->
            _movieItem = movieItem
            initMovie()

            viewModel.favouriteMovies.observe(viewLifecycleOwner) {
                _favouriteMovies = it as ArrayList
                setIconFavouriteMovie()
            }
        }

        viewModel.errorInternetNotification.observe(viewLifecycleOwner) {
            it.handled = true
            Snackbar.make(binding.root, R.string.error_internet, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.progressBar.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.commentsMovie.observe(viewLifecycleOwner) {
            commentsMovieAdapter.submitList(it)
        }

        viewModel.videosMovie.observe(viewLifecycleOwner) {
            videoMovieAdapter.submitList(it)
        }
    }

    override fun onClick(p0: View?) {
        if (favouriteMovies.contains(movieItem)) {
            viewModel.deleteFavouriteMovieDB(movieItem.movieId)
        } else {
            viewModel.addFavouriteMovieDB(movieItem)
        }
    }

    private fun setIconFavouriteMovie() {
        if (favouriteMovies.contains(movieItem)) {
            binding.ivAddToFavourite.setImageResource(R.drawable.ic_favourite_add)
        } else {
            binding.ivAddToFavourite.setImageResource(R.drawable.ic_favourite_delete)
        }
    }

    private fun initMovie() {
        Picasso.get().load(movieItem.bigPoster).placeholder(R.drawable.ic_image)
            .into(binding.ivBigPoster)
        binding.movieInfo.tvTitle.text = movieItem.title
        binding.movieInfo.tvTitleOriginal.text = movieItem.titleOriginal
        binding.movieInfo.tvDescription.text = movieItem.description
        binding.movieInfo.tvRating.text = movieItem.rating.toString()
        binding.movieInfo.tvReleaseDate.text = movieItem.releaseDate
    }
}