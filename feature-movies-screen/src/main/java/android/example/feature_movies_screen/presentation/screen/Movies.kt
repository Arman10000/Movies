package android.example.feature_movies_screen.presentation.screen

import android.content.Context
import android.example.core.adapter.MovieAdapter
import android.example.core.adapter.layoutManager.MyGridLayoutManager
import android.example.core.const.OPEN_BY_MOVIES
import android.example.core.navigation.navigate
import android.example.feature_movies_screen.R
import android.example.feature_movies_screen.databinding.MoviesBinding
import android.example.feature_movies_screen.di.provider.MoviesComponentProvider
import android.example.feature_movies_screen.presentation.enum.TypeSortEnum
import android.example.feature_movies_screen.presentation.viewModel.MoviesViewModel
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class Movies : Fragment(R.layout.movies), View.OnClickListener, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    @Inject
    lateinit var viewModel: MoviesViewModel
    private var _binding: MoviesBinding? = null
    private val binding get() = _binding!!
    private var _movieAdapter: MovieAdapter? = null
    private val movieAdapter get() = _movieAdapter!!
    private var isSearchOpen = false
    private var searchQuery = ""
    private var _menu: Menu? = null
    private val menu: Menu get() = _menu!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _movieAdapter = null
        _menu = null
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MoviesComponentProvider)
            .getMoviesComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        _menu = menu
        inflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search)
        searchView.setOnQueryTextListener(this)

        val searchMenuItem = menu.findItem(R.id.action_search)
        searchMenuItem.setOnActionExpandListener(this)
        if (isSearchOpen) {
            searchMenuItem.expandActionView()
            searchView.clearFocus()
            getSearchAutoComplete(searchView).setText(searchQuery)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchQuery = it
            viewModel.getQueryMovies(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?) = false

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        menu.setGroupVisible(0, false)
        binding.switchSort.visibility = View.GONE
        binding.textPopularity.visibility = View.GONE
        binding.textTopRated.visibility = View.GONE
        binding.refreshLayout.isEnabled = false
        if (!isSearchOpen) movieAdapter.submitList(null)
        isSearchOpen = true
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        menu.setGroupVisible(0, true)
        binding.switchSort.visibility = View.VISIBLE
        binding.textPopularity.visibility = View.VISIBLE
        binding.textTopRated.visibility = View.VISIBLE
        binding.refreshLayout.isEnabled = true
        isSearchOpen = false
        viewModel.getMoviesPageOne()
        return true
    }

    private fun getSearchAutoComplete(search: SearchView): SearchView.SearchAutoComplete {
        val l1 = search.getChildAt(0) as LinearLayout
        val l2 = l1.getChildAt(2) as LinearLayout
        val l3 = l2.getChildAt(1) as LinearLayout
        return l3.getChildAt(0) as SearchView.SearchAutoComplete
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = MoviesBinding.bind(view)

        viewModel.ifFirstStartMovies()

        _movieAdapter = MovieAdapter(
            openDetailsMovie = {
                navigate(R.id.action_movies_to_detailsMovie, it.movieId, OPEN_BY_MOVIES)
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
            viewModel.refreshLayout()
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