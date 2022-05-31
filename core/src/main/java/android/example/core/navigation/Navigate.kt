package android.example.core.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

private const val MOVIE_ID = "movieId"
private const val OPEN_BY = "openBy"

fun Fragment.navigate(actionId: Int) {
    findNavController().navigate(actionId)
}

fun Fragment.navigate(actionId: Int, movieId: Int, openBy: String) {
    findNavController().navigate(
        actionId,
        bundleOf(
            MOVIE_ID to movieId,
            OPEN_BY to openBy
        )
    )
}

fun Fragment.getMovieIdArgument(): Int? = arguments?.getInt(MOVIE_ID)
fun Fragment.getOpenByArgument(): String? = arguments?.getString(OPEN_BY)