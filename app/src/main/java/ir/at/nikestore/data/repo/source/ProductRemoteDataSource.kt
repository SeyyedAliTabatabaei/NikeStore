package ir.at.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product
import ir.at.nikestore.sevices.http.ApiService

class ProductRemoteDataSource(val apiService: ApiService) : ProductDateSource {
    override fun getProducts(sort:Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override fun getFavoriteProduct(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(product: Product): Completable {
        TODO("Not yet implemented")
    }
}