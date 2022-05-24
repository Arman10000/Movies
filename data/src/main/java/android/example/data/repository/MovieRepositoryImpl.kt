package android.example.data.repository

import android.example.data.api.MoviesApi
import android.example.data.db.MoviesDao
import android.example.data.mapper.CommentsMovieMapper
import android.example.data.mapper.MovieMapper
import android.example.data.mapper.VideosMovieMapper
import android.example.domain.item.CommentMovieItem
import android.example.domain.item.MovieItem
import android.example.domain.item.VideoMovieItem
import android.example.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
    private val movieMapper: MovieMapper,
    private val commentsMovieMapper: CommentsMovieMapper,
    private val videosMovieMapper: VideosMovieMapper
) : MovieRepository {

    override suspend fun getAllMoviesDB(): List<MovieItem> =
        movieMapper.mapListMovieModelDBToListEntity(list = moviesDao.getAllMovies())

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        movieMapper.mapListFavouriteMovieModelToListEntity(list = moviesDao.getAllFavouriteMovies())

    override suspend fun getMovieDB(movieId: Int): MovieItem =
        movieMapper.mapMovieModelDBToEntity(movieModelDB = moviesDao.getMovie(movieId))

    override suspend fun getFavouriteMovieDB(movieId: Int): MovieItem =
        movieMapper.mapFavouriteMovieModelToEntity(moviesDao.getFavouriteMovie(movieId))

    override fun getMoviesApi(
        sortBy: String,
        page: Int,
        lang: String
    ): List<MovieItem>? = movieMapper.mapListMovieModelApiToListEntity(
        moviesApi.getMovies(sortBy, page, lang).execute()
    )

    override fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): List<CommentMovieItem>? = commentsMovieMapper.mapListCommentsMovieModelApiToListEntity(
        response = moviesApi.getCommentsMovie(movieId, lang).execute()
    )


    override suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem> = commentsMovieMapper.mapListCommentsMovieModelDBToListEntity(
        list = moviesDao.getCommentsMovie(movieId)
    )

    override fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): List<VideoMovieItem>? = videosMovieMapper.mapListVideoMovieModelApiToListEntity(
        response = moviesApi.getVideosMovie(movieId, lang).execute()
    )

    override suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem> = videosMovieMapper.mapListVideoMovieModelDBToListEntity(
        list = moviesDao.getVideoMovie(movieId)
    )

    override suspend fun addMoviesDB(movies: List<MovieItem>) {
        moviesDao.addMovies(movies = movieMapper.mapListEntityToListMovieModelDB(movies))
    }

    override suspend fun addFavouriteMovieDB(movieItem: MovieItem) {
        moviesDao.addFavouriteMovie(
            favouriteMovieModel = movieMapper.mapEntityToFavouriteMovieModel(movieItem)
        )
    }

    override suspend fun addCommentsMovieDB(comments: List<CommentMovieItem>, movieId: Int) {
        moviesDao.addCommentsMovie(
            comments = commentsMovieMapper.mapListEntityToListCommentsMovieModelDB(
                list = comments,
                movieId
            )
        )
    }

    override suspend fun addVideosMovieDB(videos: List<VideoMovieItem>, movieId: Int) {
        moviesDao.addVideosMovie(
            video = videosMovieMapper.mapListEntityToListVideoMovieModelDB(
                list = videos,
                movieId
            )
        )
    }

    override suspend fun deleteCommentsMovieDB(movieId: Int) {
        moviesDao.deleteCommentsMovie(movieId)
    }

    override suspend fun deleteVideosMovieDB(movieId: Int) {
        moviesDao.deleteVideosMovie(movieId)
    }

    override suspend fun deleteFavouriteMovieDB(movieId: Int) {
        moviesDao.deleteFavouriteMovie(movieId)
    }

    override suspend fun clearMoviesDB() {
        moviesDao.clearMovies()
    }
}