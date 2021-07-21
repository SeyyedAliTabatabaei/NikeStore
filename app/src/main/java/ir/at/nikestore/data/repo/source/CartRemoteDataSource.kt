package ir.at.nikestore.data.repo.source

import com.google.gson.JsonObject
import io.reactivex.Single
import ir.at.nikestore.data.AddToCartResponse
import ir.at.nikestore.data.CartItemCount
import ir.at.nikestore.data.CartResponse
import ir.at.nikestore.data.MessageResponse
import ir.at.nikestore.sevices.http.ApiService

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id" , productId)
        }
    )

    override fun get(): Single<CartResponse> = apiService.getCart()

    override fun remove(cartItemId: Int): Single<MessageResponse> = apiService.removeItemFromCart(
        JsonObject().apply {
            addProperty("cart_item_id" , cartItemId)
        }
    )

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>  = apiService.changeCount(
        JsonObject().apply {
            addProperty("cart_item_id" , cartItemId)
            addProperty("count" , count)
        }
    )

    override fun getCartItemsCount(): Single<CartItemCount> = apiService.getCartItemCount()
}