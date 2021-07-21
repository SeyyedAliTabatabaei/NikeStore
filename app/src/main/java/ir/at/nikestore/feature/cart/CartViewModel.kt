package ir.at.nikestore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.TokenContainer
import io.reactivex.Completable
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.CartItem
import ir.at.nikestore.data.CartResponse
import ir.at.nikestore.data.PurchaseDetail
import ir.at.nikestore.data.repo.CartRepository

class CartViewModel(val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    private fun getCartItems(){
        progressBarLiveData.value = true
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable){
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()){
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value = PurchaseDetail(t.total_price , t.shipping_cost , t.payable_price)
                        }
                    }

                })
        }
    }


    fun removeItemFromCart(cartItem: CartItem) : Completable{
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem) : Completable =
        cartRepository.changeCount(cartItem.cart_item_id , ++cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()

    fun decreaseCartItemCount(cartItem: CartItem) : Completable =
        cartRepository.changeCount(cartItem.cart_item_id , --cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()

    fun refresh() = getCartItems()

    private fun calculateAndPublishPurchaseDetail(){

        cartItemsLiveData.value?.let {cartItems ->
            purchaseDetailLiveData.value?.let {
                var totalPrice = 0
                var payablePrice = 0

                cartItems.forEach {

                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                it.totalPrice = totalPrice
                it.payable_price = payablePrice
                purchaseDetailLiveData.postValue(it)
            }
        }
    }

}