package ir.at.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product

class ProductLocalDataSource : ProductDateSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(): Completable {
        TODO("Not yet implemented")
    }
}