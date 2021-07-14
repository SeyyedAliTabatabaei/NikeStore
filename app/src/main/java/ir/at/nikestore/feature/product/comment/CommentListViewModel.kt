package com.sevenlearn.nikestore.feature.product.comment

import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.Comment
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.repo.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : NikeViewModel() {
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }
}