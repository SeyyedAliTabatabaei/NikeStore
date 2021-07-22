package ir.at.nikestore.feature.main

import com.sevenlearn.nikestore.data.TokenContainer
import io.reactivex.schedulers.Schedulers
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.CartItemCount
import ir.at.nikestore.data.repo.CartRepository
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) : NikeViewModel() {

    fun getCartItemsCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }
}