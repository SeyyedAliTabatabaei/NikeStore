package ir.at.nikestore.data.repo.source

import io.reactivex.Single
import ir.at.nikestore.data.AddToCartResponse
import ir.at.nikestore.data.CartItemCount
import ir.at.nikestore.data.CartResponse
import ir.at.nikestore.data.MessageResponse

interface CartDataSource {
    fun addToCart(productId : Int) : Single<AddToCartResponse>
    fun get() : Single<CartResponse>
    fun remove(cartItemId : Int) : Single<MessageResponse>
    fun changeCount(cartItemId : Int , count : Int) : Single<AddToCartResponse>
    fun getCartItemsCount() : Single<CartItemCount>
}