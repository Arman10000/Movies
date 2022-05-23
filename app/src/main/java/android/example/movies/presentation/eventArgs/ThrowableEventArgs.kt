package android.example.movies.presentation.eventArgs

import ru.ar2code.mutableliveevent.EventArgs

class ThrowableEventArgs(
    error: Throwable
) : EventArgs<Throwable>(error)