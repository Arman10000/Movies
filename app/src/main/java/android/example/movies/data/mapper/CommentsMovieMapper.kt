package android.example.movies.data.mapper

import android.example.movies.data.db.model.CommentsMovieModelDB
import android.example.movies.domain.item.CommentMovieItem

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
        nameAuthor = commentsMovieModelDB.nameAuthor,
        comment = commentsMovieModelDB.comment
    )

    fun mapListEntityToListCommentsMovieModelDB(
        list: List<CommentMovieItem>,
        movieId: Int
    ) = list.map {
        mapEntityToCommentsMovieModelDB(
            commentsMovieItem = it,
            movieId = movieId
        )
    }

    fun mapListCommentsMovieModelDBToListEntity(
        list: List<CommentsMovieModelDB>
    ) = list.map {
        mapCommentsMovieModelDBToEntity(
            commentsMovieModelDB = it
        )
    }
}