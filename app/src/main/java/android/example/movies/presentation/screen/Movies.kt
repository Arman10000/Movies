package android.example.movies.presentation.screen

import android.content.Context
import android.example.movies.R
import android.example.movies.data.api.MoviesApi
import android.example.movies.databinding.MoviesBinding
import android.example.movies.presentation.adapter.MovieAdapter
import android.example.movies.presentation.di.app.App
import android.example.movies.presentation.viewModel.MovieViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class Movies : Fragment(R.layout.movies), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val movieViewModel by lazy {
        activity?.let {
            ViewModelProvider(
                it,
                viewModelFactory
            )[MovieViewModel::class.java]
        }
    }
    private var _binding: MoviesBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MoviesBinding.bind(view)

        movieViewModel?.ifFirstStartMovies()

        val movieAdapter = MovieAdapter(
            openDetailMovie = {
                movieViewModel?.saveDetailsMovie(movieItem = it)
                findNavController().navigate(
                    MoviesDirections.actionMoviesToDetailMovie()
                )
            }
        )

        movieViewModel?.errorInternetNotification?.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, R.string.error_internet, Snackbar.LENGTH_SHORT).show()
            movieViewModel?.stopLoadingMovies()
        }

        movieViewModel?.progressBar?.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.show()
            else binding.progressBar.hide()
        }

        movieViewModel?.movies?.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
            movieViewModel?.stopLoadingMovies()
        }

        movieViewModel?.let {
            binding.rvMovies.layoutManager = GridLayoutManager(context, it.getColumnCount())
        }

        binding.rvMovies.adapter = movieAdapter
        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val showItemCount = layoutManager.findLastVisibleItemPosition()

                movieViewModel?.loadingMoviesIfEndList(
                    totalItemCount = totalItemCount,
                    visibleItemCount = visibleItemCount,
                    showItemCount = showItemCount
                )

            }
        })

        binding.switchSort.setOnClickListener(this)
        binding.textPopularity.setOnClickListener(this)
        binding.textTopRated.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setSelectedTypeSortMovies()
    }

    override fun onClick(view: View?) {

        val typeSort = when (view?.id) {

            R.id.switchSort -> {
                if (binding.switchSort.isChecked)
                    MoviesApi.SORT_BY_TOP_RATED
                else
                    MoviesApi.SORT_BY_POPULARITY
            }

            R.id.textPopularity -> {
                MoviesApi.SORT_BY_POPULARITY
            }

            R.id.textTopRated -> {
                MoviesApi.SORT_BY_TOP_RATED
            }

            else -> {
                MoviesApi.SORT_BY_POPULARITY
            }
        }

        movieViewModel?.loadingMoviesBySelectedTypeSort(
            typeSort = typeSort
        )

        setSelectedTypeSortMovies()

    }

    private fun setSelectedTypeSortMovies() {
        context?.let {
            if (movieViewModel?.typeSort == MoviesApi.SORT_BY_POPULARITY) {
                binding.textPopularity.setTextColor(ContextCompat.getColor(it, R.color.teal_200))
                binding.textTopRated.setTextColor(ContextCompat.getColor(it, R.color.white))
                binding.switchSort.isChecked = false
            } else {
                binding.textTopRated.setTextColor(ContextCompat.getColor(it, R.color.teal_200))
                binding.textPopularity.setTextColor(ContextCompat.getColor(it, R.color.white))
                binding.switchSort.isChecked = true
            }
        }
    }

}