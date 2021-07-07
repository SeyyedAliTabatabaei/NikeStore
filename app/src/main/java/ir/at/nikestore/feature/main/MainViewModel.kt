package ir.at.nikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.impl.Schedulers
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.ProductRepository
import timber.log.Timber

class MainViewModel(productRepository: ProductRepository) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()


    init {
        progressBarLiveData.value = true
        productRepository.getProducts()
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<List<Product>>{
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

                override fun onError(e: Throwable) {
                }

            })

    }
}

