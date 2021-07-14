package ir.at.nikestore.data.repo.source

import com.sevenlearn.nikestore.data.Comment
import io.reactivex.Single
import ir.at.nikestore.sevices.http.ApiService

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun getAll(productId : Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}