package com.sevenlearn.nikestore.feature.order

import androidx.lifecycle.MutableLiveData
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import com.sevenlearn.nikestore.data.OrderHistoryItem
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.common.NikeSingleObserver
import ir.at.nikestore.data.repo.order.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {
    val orders = MutableLiveData<List<OrderHistoryItem>>()

    init {
        progressBarLiveData.value = true
        orderRepository.list().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orders.value=t
                }
            })
    }
}