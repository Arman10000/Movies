package android.example.movies.presentation.adapter.layoutManager

import android.content.Context
import android.content.res.Resources
import android.example.movies.data.api.MoviesApi
import androidx.recyclerview.widget.GridLayoutManager

class MyGridLayoutManager(
    context: Context
) : GridLayoutManager(context, getColumnCount()) {

    companion object {
        private fun getColumnCount(): Int {
            val displayMetrics = Resources.getSystem().displayMetrics
            val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
            val posterSize = MoviesApi.SMALL_POSTER_SIZE.toInt()
            return if (width / posterSize > 2) width / posterSize else 2
        }
    }

}