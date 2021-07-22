package ir.at.nikestore.feature.shipping

import com.sevenlearn.nikestore.data.SubmitOrderResult
import io.reactivex.Single
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.data.repo.order.OrderRepository

const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(private val orderRepository: OrderRepository) : NikeViewModel() {

    fun submitOrder(firstName: String, lastName: String, postalCode: String, phoneNumber: String, address: String, paymentMethod: String): Single<SubmitOrderResult> {
        return orderRepository.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)
    }

}