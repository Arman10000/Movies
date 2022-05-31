package android.example.core.viewModel

import android.example.core.eventArgs.ThrowableEventArgs
import android.example.core.item.MovieItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ru.ar2code.mutableliveevent.MutableLiveEvent

abstract class BaseViewModel : ViewModel() {

    protected val errorInternetNotificationBase: MutableLiveEvent<ThrowableEventArgs> =
        MutableLiveEvent()
    protected val progressBarBase: MutableLiveData<Boolean> = MutableLiveData()
    protected val favouriteMoviesBase: MutableLiveData<List<MovieItem>> = MutableLiveData()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    val errorInternetNotification: LiveData<ThrowableEventArgs> = errorInternetNotificationBase
    val progressBar: LiveData<Boolean> = progressBarBase
    val favouriteMovies: LiveData<List<MovieItem>> = favouriteMoviesBase

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}