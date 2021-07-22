package ir.at.nikestore.data.repo

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.repo.source.ProductDateSource
import ir.at.nikestore.data.repo.source.ProductLocalDataSource
import ir.at.nikestore.data.repo.source.ProductRemoteDataSource

class ProductRepositoryImpl(val remoteDataSource: ProductDateSource , val productLocalDataSource: ProductLocalDataSource) : ProductRepository {


    override fun getProducts(sort:Int): Single<List<Product>> = productLocalDataSource.getFavoriteProduct()
        .flatMap { favoriteProduct ->
            remoteDataSource.getProducts(sort).doOnSuccess {
                val favoriteProductsIds  = favoriteProduct.map {
                    it.id
                }

                it.forEach { product ->
                    if (favoriteProductsIds.contains(product.id)){
                        product.isFavorite = true
                    }
                }
            }
        }

    override fun getFavoriteProduct(): Single<List<Product>> = productLocalDataSource.getFavoriteProduct()

    override fun addToFavorite(product: Product): Completable = productLocalDataSource.addToFavorite(product)

    override fun deleteFromFavorite(product: Product): Completable = productLocalDataSource.deleteFromFavorite(product)
}