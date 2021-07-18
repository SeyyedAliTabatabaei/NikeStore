package ir.at.nikestore.data.repo

import io.reactivex.Single
import ir.at.nikestore.data.AddToCartResponse
import ir.at.nikestore.data.CartItemCount
import ir.at.nikestore.data.CartResponse
import ir.at.nikestore.data.MessageResponse
import ir.at.nikestore.data.repo.source.CartDataSource
import ir.at.nikestore.data.repo.source.CartRemoteDataSource

class CartRepositoryImpl(val remoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = remoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}