package com.sevenlearn.nikestore.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.Checkout
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.repo.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {
    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }
            })
    }
}