package ir.at.nikestore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.impl.Schedulers
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.Banner
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeCompletableObserver
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.SORT_LATEST
import ir.at.nikestore.data.SORT_POPULAR
import ir.at.nikestore.data.repo.BannerRepository
import ir.at.nikestore.data.repo.ProductRepository
import timber.log.Timber

class HomeViewModel(private val productRepository: ProductRepository, bannerRepository: BannerRepository) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    val productsPopularLiveData = MutableLiveData<List<Product>>()
    val bannerLiveData = MutableLiveData<List<Banner>>()


    init {
        progressBarLiveData.value = true
        productRepository.getProducts(SORT_LATEST)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

            })

        productRepository.getProducts(SORT_POPULAR)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productsPopularLiveData.value = t
                }

            })

        bannerRepository.getBanners()
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object  : NikeSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value = t
                }
            })

    }

    fun addProductToFavorites(product: Product){
        if (product.isFavorite)
            productRepository.deleteFromFavorite(product)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(object  : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavorite(product)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .subscribe(object  : NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })

    }
}

