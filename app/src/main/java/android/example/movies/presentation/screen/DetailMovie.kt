package android.example.movies.presentation.screen

import android.content.Context
import android.content.Intent
import android.example.movies.R
import android.example.movies.databinding.DetailMovieBinding
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.presentation.adapter.CommentsMovieAdapter
import android.example.movies.presentation.adapter.VideoMovieAdapter
import android.example.movies.presentation.di.app.App
import android.example.movies.presentation.viewModel.MoviesViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.util.*
import javax.inject.Inject

class DetailMovie : Fragment(R.layout.detail_movie), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val movieViewModel by lazy {
        activity?.let {
            ViewModelProvider(
                it,
                viewModelFactory
            )[MoviesViewModel::class.java]
        }
    }
    private var _binding: DetailMovieBinding? = null
    private var _movieItem: MovieItem? = null
    private var _favouriteMovieItem: FavouriteMovieItem? = null
    private var _favouriteMovies: ArrayList<FavouriteMovieItem>? = null
    private val binding get() = _binding!!
    private val movieItem get() = _movieItem!!
    private val favouriteMovieItem get() = _favouriteMovieItem!!
    private val favouriteMovies: ArrayList<FavouriteMovieItem> get() = _favouriteMovies!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _movieItem = null
        _favouriteMovieItem = null
        _favouriteMovies = null
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.setGroupVisible(0, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = DetailMovieBinding.bind(view)
        _movieItem = movieViewModel?.getDetailsMovie()
        _favouriteMovieItem = movieItem.toFavouriteMovieItem()

        movieViewModel?.loadingDetailMovie(movieItem.movieId)

        binding.ivAddToFavourite.setOnClickListener(this)
        binding.movieInfo.rvVideos.layoutManager = LinearLayoutManager(context)
        binding.movieInfo.rvComments.layoutManager = LinearLayoutManager(context)

        val commentsMovieAdapter = CommentsMovieAdapter()
        val videoMovieAdapter = VideoMovieAdapter(
            openVideo = {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                } catch (t: Throwable) {
                    Snackbar.make(binding.root, R.string.not_action_view, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        )

        binding.movieInfo.rvComments.adapter = commentsMovieAdapter
        binding.movieInfo.rvVideos.adapter = videoMovieAdapter

        initMovie()

        movieViewModel?.errorInternetNotification?.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, R.string.error_internet, Snackbar.LENGTH_SHORT).show()
        }

        movieViewModel?.progressBar?.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.show()
            else binding.progressBar.hide()
        }

        movieViewModel?.favouriteMovies?.observe(viewLifecycleOwner) {
            _favouriteMovies = it as ArrayList
            setIconFavouriteMovie()
        }

        movieViewModel?.commentsMovie?.observe(viewLifecycleOwner) {
            commentsMovieAdapter.submitList(it)
        }

        movieViewModel?.videosMovie?.observe(viewLifecycleOwner) {
            videoMovieAdapter.submitList(it)
        }

    }

    override fun onClick(p0: View?) {

        if (favouriteMovies.contains(favouriteMovieItem)) {
            movieViewModel?.deleteFavouriteMovieDB(movieId = movieItem.movieId)
        } else {
            movieViewModel?.addFavouriteMovieDB(favouriteMovieItem = favouriteMovieItem)
        }

    }

    private fun setIconFavouriteMovie() {

        if (favouriteMovies.contains(favouriteMovieItem)) {
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