package ir.at.nikestore.data

import ir.at.nikestore.data.Product

data class CartItem(
    val cart_item_id: Int,
    val count: Int,
    val product: Product
)