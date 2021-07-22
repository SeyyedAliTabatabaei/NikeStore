package ir.at.nikestore.feature.favorite

import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeCompletableObserver
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.ProductRepository

class FavoriteProductsViewModel(private val productRepository: ProductRepository) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProduct()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.postValue(t)
                }
            })
    }

    fun removeFromFavorite(product: Product){
        productRepository.deleteFromFavorite(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {

                }
            })
    }

}