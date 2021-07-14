package ir.at.nikestore.data.repo

import com.sevenlearn.nikestore.data.Comment
import io.reactivex.Single
import ir.at.nikestore.data.repo.source.CommentDataSource

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) : CommentRepository {
    override fun getAll(productId : Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}