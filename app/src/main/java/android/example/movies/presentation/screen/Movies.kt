package android.example.movies.presentation.screen

import android.content.Context
import android.example.movies.R
import android.example.movies.databinding.MoviesBinding
import android.example.movies.di.app.App
import android.example.movies.presentation.adapter.MovieAdapter
import android.example.movies.presentation.adapter.layoutManager.MyGridLayoutManager
import android.example.movies.presentation.enum.TypeSortEnum
import android.example.movies.presentation.viewModel.MoviesViewModel
import android.example.movies.presentation.viewModel.ViewModelFactory
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class Movies : Fragment(R.layout.movies), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MoviesViewModel by viewModels { viewModelFactory }
    private var _binding: MoviesBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MoviesBinding.bind(view)

        viewModel.ifFirstStartMovies()

        val movieAdapter = MovieAdapter(
            openDetailsMovie = {
                findNavController().navigate(
                    MoviesDirections.actionMoviesToDetailsMovie(it.movieId, OPEN_BY_MOVIES)
                )
            }
        )

        viewModel.progressBar.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }

        viewModel.setSelectedTypeSortMovies.observe(viewLifecycleOwner) {
            val selectedTextColor = ContextCompat.getColor(requireContext(), R.color.teal_200)
            val notSelectedTextColor = ContextCompat.getColor(requireContext(), R.color.white)

            binding.switchSort.isChecked = if (it.selectedSwitchSortState) {
                binding.textPopularity.setTextColor(notSelectedTextColor)
                binding.textTopRated.setTextColor(selectedTextColor)
                it.selectedSwitchSortState
            } else {
                binding.textPopularity.setTextColor(selectedTextColor)
                binding.textTopRated.setTextColor(notSelectedTextColor)
                it.selectedSwitchSortState
            }
        }

        viewModel.errorInternetNotification.observe(viewLifecycleOwner) {
            it.handled = true
            Snackbar.make(binding.root, R.string.error_internet, Snackbar.LENGTH_SHORT).show()
        }

        binding.rvMovies.layoutManager = MyGridLayoutManager(requireContext())

        binding.rvMovies.adapter = movieAdapter
        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val showItemCount = layoutManager.findLastVisibleItemPosition()

                viewModel.loadingMoviesIfEndList(
                    totalItemCount,
                    visibleItemCount,
                    showItemCount
                )

            }
        })

        binding.refreshLayout.setOnRefreshListener {
            viewModel.startLoadingMovies()
            binding.refreshLayout.isRefreshing = false
        }

        binding.switchSort.setOnClickListener(this)
        binding.textPopularity.setOnClickListener(this)
        binding.textTopRated.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val typeSortEnum = when (view?.id) {
            R.id.switchSort -> {
                TypeSortEnum.SWITCH_SORT
            }

            R.id.textPopularity -> {
                TypeSortEnum.TEXT_POPULARITY
            }

            R.id.textTopRated -> {
                TypeSortEnum.TEXT_TOP_RATED
            }

            else -> {
                null
            }
        }

        typeSortEnum?.let {
            viewModel.loadingMoviesBySelectedTypeSort(typeSortEnum)
        }
    }

}