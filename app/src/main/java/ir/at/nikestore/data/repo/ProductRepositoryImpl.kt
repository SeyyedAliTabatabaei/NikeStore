package ir.at.nikestore.data.repo

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.source.ProductDateSource
import ir.at.nikestore.data.repo.source.ProductLocalDataSource
import ir.at.nikestore.data.repo.source.ProductRemoteDataSource

class ProductRepositoryImpl(val remoteDataSource: ProductDateSource , val productLocalDataSource: ProductLocalDataSource) : ProductRepository {
    override fun getProducts(sort:Int): Single<List<Product>> = remoteDataSource.getProducts(sort)

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