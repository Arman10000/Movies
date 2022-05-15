package android.example.movies.data.api.model

import android.example.movies.domain.item.CommentMovieItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentsMovieModelApi(
    @SerializedName("author") @Expose
    val nameAuthor: String,
    @SerializedName("content") @Expose
    val comment: String
) {

    fun toCommentsMovieItem() = CommentMovieItem(
        nameAuthor = this.nameAuthor,
        comment = this.comment
    )

}