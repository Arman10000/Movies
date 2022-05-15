package android.example.movies.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val viewModelProviders: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}