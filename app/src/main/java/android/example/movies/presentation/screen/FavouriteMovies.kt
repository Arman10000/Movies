package android.example.movies.presentation.screen

import android.content.Context
import android.example.movies.R
import android.example.movies.databinding.FavouriteMoviesBinding
import android.example.movies.domain.item.MovieItem
import android.example.movies.presentation.adapter.MovieAdapter
import android.example.movies.presentation.adapter.layoutManager.MyGridLayoutManager
import android.example.movies.presentation.di.app.App
import android.example.movies.presentation.viewModel.MoviesViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import javax.inject.Inject

class FavouriteMovies : Fragment(R.layout.favourite_movies) {

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
    private var _binding: FavouriteMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        _binding = FavouriteMoviesBinding.bind(view)

        val movieAdapter = MovieAdapter(
            openDetailMovie = {
                movieViewModel?.saveDetailsMovie(movieItem = it)
                findNavController().navigate(
                    FavouriteMoviesDirections.actionFavouriteMoviesToDetailMovie()
                )
            }
        )

        movieViewModel?.favouriteMovies?.observe(viewLifecycleOwner) { favouriteMovieList ->
            val movies = arrayListOf<MovieItem>()
            favouriteMovieList.forEach {
                movies.add(it.toMovieItem())
            }
            movieAdapter.submitList(movies)
        }

        context?.let {
            binding.rvFavouriteMovies.layoutManager = MyGridLayoutManager(context = it)
        }

        binding.rvFavouriteMovies.adapter = movieAdapter

    }

}