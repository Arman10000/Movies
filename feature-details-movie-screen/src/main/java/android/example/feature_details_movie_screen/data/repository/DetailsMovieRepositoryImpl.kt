package android.example.feature_details_movie_screen.data.repository

import android.example.core.db.MoviesDao
import android.example.core.item.MovieItem
import android.example.feature_details_movie_screen.data.api.DetailsMovieApi
import android.example.feature_details_movie_screen.data.mapper.CommentsMovieMapper
import android.example.feature_details_movie_screen.data.mapper.DetailsMovieMapper
import android.example.feature_details_movie_screen.data.mapper.VideosMovieMapper
import android.example.feature_details_movie_screen.domain.repository.DetailsMovieRepository
import android.example.feature_details_movie_screen.domain.item.CommentMovieItem
import android.example.feature_details_movie_screen.domain.item.VideoMovieItem

class DetailsMovieRepositoryImpl(
    private val detailsMovieApi: DetailsMovieApi,
    private val moviesDao: MoviesDao,
    private val detailsMovieMapper: DetailsMovieMapper,
    private val commentsMovieMapper: CommentsMovieMapper,
    private val videosMovieMapper: VideosMovieMapper
) : DetailsMovieRepository {

    override suspend fun getAllFavouriteMoviesDB(): List<MovieItem> =
        detailsMovieMapper.mapListFavouriteMovieModelToListEntity(list = moviesDao.getAllFavouriteMovies())

    override suspend fun getMovieDB(movieId: Int): MovieItem =
        detailsMovieMapper.mapMovieModelDBToEntity(movieModelDB = moviesDao.getMovie(movieId))


    override suspend fun getFavouriteMovieDB(movieId: Int): MovieItem =
        detailsMovieMapper.mapFavouriteMovieModelToEntity(moviesDao.getFavouriteMovie(movieId))

    override fun getCommentsMovieApi(
        movieId: Int,
        lang: String
    ): List<CommentMovieItem> = commentsMovieMapper.mapListCommentsMovieModelApiToListEntity(
        response = detailsMovieApi.getCommentsMovie(movieId, lang).execute()
    )


    override suspend fun getCommentsMovieDB(
        movieId: Int
    ): List<CommentMovieItem> = commentsMovieMapper.mapListCommentsMovieModelDBToListEntity(
        list = moviesDao.getCommentsMovie(movieId)
    )

    override fun getVideosMovieApi(
        movieId: Int,
        lang: String
    ): List<VideoMovieItem> = videosMovieMapper.mapListVideoMovieModelApiToListEntity(
        response = detailsMovieApi.getVideosMovie(movieId, lang).execute()
    )

    override suspend fun getVideosMovieDB(
        movieId: Int
    ): List<VideoMovieItem> = videosMovieMapper.mapListVideoMovieModelDBToListEntity(
        list = moviesDao.getVideoMovie(movieId)
    )

    override suspend fun addFavouriteMovieDB(movieItem: MovieItem) {
        moviesDao.addFavouriteMovie(
            favouriteMovieModel = detailsMovieMapper.mapEntityToFavouriteMovieModel(movieItem)
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
}