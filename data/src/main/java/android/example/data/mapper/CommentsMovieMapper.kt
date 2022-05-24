package android.example.data.mapper

import android.example.data.api.model.CommentsMovieList
import android.example.data.api.model.CommentsMovieModelApi
import android.example.data.db.model.CommentsMovieModelDB
import android.example.domain.item.CommentMovieItem
import retrofit2.Response

class CommentsMovieMapper {

    private fun mapEntityToCommentsMovieModelDB(
        commentsMovieItem: CommentMovieItem,
        movieId: Int
    ) = CommentsMovieModelDB(
        movieId = movieId,
        nameAuthor = commentsMovieItem.nameAuthor,
        comment = commentsMovieItem.comment
    )

    private fun mapCommentsMovieModelDBToEntity(
        commentsMovieModelDB: CommentsMovieModelDB
    ) = CommentMovieItem(
        commentsMovieModelDB.nameAuthor,
        commentsMovieModelDB.comment
    )

    private fun mapCommentsMovieModelApiToEntity(
        commentsMovieModelApi: CommentsMovieModelApi
    ) = CommentMovieItem(
        commentsMovieModelApi.nameAuthor,
        commentsMovieModelApi.comment
    )

    fun mapListEntityToListCommentsMovieModelDB(
        list: List<CommentMovieItem>,
        movieId: Int
    ) = list.map {
        mapEntityToCommentsMovieModelDB(
            commentsMovieItem = it,
            movieId
        )
    }

    fun mapListCommentsMovieModelDBToListEntity(
        list: List<CommentsMovieModelDB>
    ) = list.map {
        mapCommentsMovieModelDBToEntity(
            commentsMovieModelDB = it
        )
    }

    fun mapListCommentsMovieModelApiToListEntity(
        response: Response<CommentsMovieList>
    ) = response.body()?.commentsMovieList?.map {
        mapCommentsMovieModelApiToEntity(
            commentsMovieModelApi = it
        )
    }
}