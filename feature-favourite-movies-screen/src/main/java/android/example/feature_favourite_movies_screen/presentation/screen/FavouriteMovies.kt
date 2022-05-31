package android.example.feature_favourite_movies_screen.presentation.screen

import android.content.Context
import android.example.core.adapter.MovieAdapter
import android.example.core.adapter.layoutManager.MyGridLayoutManager
import android.example.core.const.OPEN_BY_FAVOURITE_MOVIES
import android.example.core.navigation.navigate
import android.example.feature_favourite_movies_screen.R
import android.example.feature_favourite_movies_screen.databinding.FavouriteMoviesBinding
import android.example.feature_favourite_movies_screen.di.provider.FavouriteMoviesComponentProvider
import android.example.feature_favourite_movies_screen.presentation.viewModel.FavouriteMoviesViewModel
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import javax.inject.Inject

class FavouriteMovies : Fragment(R.layout.favourite_movies) {

    @Inject
    lateinit var viewModel: FavouriteMoviesViewModel
    private var _binding: FavouriteMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as FavouriteMoviesComponentProvider)
            .getFavouriteMoviesComponent().inject(this)
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
                navigate(
                    R.id.action_favouriteMovies_to_detailMovie,
                    it.movieId,
                    OPEN_BY_FAVOURITE_MOVIES
                )
            }
        )

        binding.rvFavouriteMovies.layoutManager = MyGridLayoutManager(requireContext())
        binding.rvFavouriteMovies.adapter = movieAdapter

        viewModel.getAllFavouriteMoviesDB()

        viewModel.favouriteMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }
    }

}