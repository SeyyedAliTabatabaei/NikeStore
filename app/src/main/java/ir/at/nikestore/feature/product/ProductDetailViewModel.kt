package ir.at.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.Comment
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.EXTRA_KEY_DATA
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.CartRepository
import ir.at.nikestore.data.repo.CommentRepository

class ProductDetailViewModel(val bundle: Bundle , commentRepository: CommentRepository ,val cartRepository: CartRepository) : NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)

        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<com.sevenlearn.nikestore.data.Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }
            })
    }

    fun onAddToCart() : Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

}