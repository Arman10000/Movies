package android.example.movies.data.repository

import android.example.movies.data.api.MoviesApi
import android.example.movies.data.api.model.CommentsMovieList
import android.example.movies.data.api.model.MovieList
import android.example.movies.data.api.model.VideosMovieList
import android.example.movies.data.db.MoviesDao
import android.example.movies.data.mapper.CommentsMovieMapper
import android.example.movies.data.mapper.FavouriteMovieMapper
import android.example.movies.data.mapper.MovieMapper
import android.example.movies.data.mapper.VideosMovieMapper
import android.example.movies.domain.item.CommentMovieItem
import android.example.movies.domain.item.FavouriteMovieItem
import android.example.movies.domain.item.MovieItem
import android.example.movies.domain.item.VideoMovieItem
import android.example.movies.domain.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import retrofit2.Response

class MovieRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val movieMapper: MovieMapper,
    private val FavouriteMovieMapper: FavouriteMovieMapper,
    private val commentsMovieMapper: CommentsMovieMapper,
    private val videosMovieMapper: VideosMovieMapper
) : MovieRepository {

    override fun getAllMoviesDB(): LiveData<List<MovieItem>> {
        return MediatorLiveData<List<MovieItem>>().apply {
            addSource(moviesDao.getAllMovies()) {
                value = movieMapper.mapListMovieModelToListEntity(list = it)
            }
        }
    }

    override fun getAllFavouriteMoviesDB(): LiveData<List<FavouriteMovieItem>> =
        MediatorLiveData<List<FavouriteMovieItem>>().apply {
            addSource(moviesDao.getAllFavouriteMovies()) {
                value = FavouriteMovieMapper.mapListFavouriteMovieModelToListEntity(list = it)
            }
        }

    override fun getMoviesApi(
        sortBy: String,
        page: Int,
        lang: String
    ): Response<MovieList> = moviesApi.getMovies(
        sortBy = sortBy,
        page = page,
        lang = lang
    ).execute()

    override fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): Response<CommentsMovieList> = moviesApi.getCommentsMovie(
        movieId = movieId,
        lang = lang
    ).execute()

    override suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem> = commentsMovieMapper.mapListCommentsMovieModelDBToListEntity(
        list = moviesDao.getCommentsMovie(movieId = movieId)
    )

    override fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): Response<VideosMovieList> = moviesApi.getVideosMovie(
        movieId = movieId,
        lang = lang
    ).execute()

    override suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem> = videosMovieMapper.mapListVideoMovieModelDBToListEntity(
        list = moviesDao.getVideoMovie(movieId = movieId)
    )

    override suspend fun addMoviesDB(
        movies: List<MovieItem>
    ) {
        moviesDao.addMovies(movies = movieMapper.mapListEntityToListMovieModelDB(movies))
    }

    override suspend fun addFavouriteMovieDB(
        favouriteMovieItem: FavouriteMovieItem
    ) {
        moviesDao.addFavouriteMovie(
            favouriteMovieModel = FavouriteMovieMapper.mapEntityToFavouriteMovieModel(
                favouriteMovieItem = favouriteMovieItem
            )
        )
    }

    override suspend fun addCommentsMovieDB(
        comments: List<CommentMovieItem>,
        movieId: Int
    ) {
        moviesDao.addCommentsMovie(
            comments = commentsMovieMapper.mapListEntityToListCommentsMovieModelDB(
                list = comments,
                movieId = movieId
            )
        )
    }

    override suspend fun addVideosMovieDB(
        videos: List<VideoMovieItem>,
        movieId: Int
    ) {
        moviesDao.addVideosMovie(
            video = videosMovieMapper.mapListEntityToListVideoMovieModelDB(
                list = videos,
                movieId = movieId
            )
        )
    }

    override suspend fun deleteCommentsMovieDB(
        movieId: Int
    ) {
        moviesDao.deleteCommentsMovie(movieId = movieId)
    }

    override suspend fun deleteVideosMovieDB(movieId: Int) {
        moviesDao.deleteVideosMovie(movieId = movieId)
    }

    override suspend fun deleteFavouriteMovieDB(
        movieId: Int
    ) {
        moviesDao.deleteFavouriteMovie(movieId = movieId)
    }

    override suspend fun clearMoviesDB() {
        moviesDao.clearMovies()
    }

}