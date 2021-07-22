package ir.at.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.R
import ir.at.nikestore.common.NikeCompletableObserver
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.ProductRepository

class ProductListViewModel(var sort : Int, val productRepository: ProductRepository) : NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(R.string.sortLatest , R.string.sortPopular , R.string.sortPriceHighToLow , R.string.sortPriceLowToHigh)

    init {
        getProduct()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    fun getProduct(){
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
    }

    fun onSelectedSortChangedByUser(sort:Int){
        this.sort = sort
        selectedSortTitleLiveData.value = sortTitles[sort]
        getProduct()
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