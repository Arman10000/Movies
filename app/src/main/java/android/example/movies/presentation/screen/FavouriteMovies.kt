package android.example.movies.presentation.screen

import android.content.Context
import android.example.movies.R
import android.example.movies.databinding.FavouriteMoviesBinding
import android.example.movies.domain.item.MovieItem
import android.example.movies.presentation.adapter.MovieAdapter
import android.example.movies.presentation.adapter.layoutManager.MyGridLayoutManager
import android.example.movies.presentation.di.app.App
import android.example.movies.presentation.viewModel.FavouriteMoviesViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class FavouriteMovies : Fragment(R.layout.favourite_movies) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: FavouriteMoviesViewModel by viewModels { viewModelFactory }
    private var _binding: FavouriteMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
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
            openDetailsMovie = {
                findNavController().navigate(
                    FavouriteMoviesDirections.actionFavouriteMoviesToDetailMovie(movieItem = it)
                )
            }
        )

        viewModel.favouriteMovies.observe(viewLifecycleOwner) { favouriteMovieList ->
            val movies = arrayListOf<MovieItem>()
            favouriteMovieList.forEach {
                movies.add(it.toMovieItem())
            }
            movieAdapter.submitList(movies)
        }

        binding.rvFavouriteMovies.layoutManager = MyGridLayoutManager(requireContext())
        binding.rvFavouriteMovies.adapter = movieAdapter
    }

}