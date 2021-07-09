package ir.at.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product

interface ProductDateSource {

    fun getProducts(sort:Int) : Single<List<Product>>

    fun getFavoriteProduct() : Single<List<Product>>

    fun addToFavorite() : Completable

    fun deleteFromFavorite() : Completable


}