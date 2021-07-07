package ir.at.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product
import ir.at.nikestore.sevices.http.ApiService

class ProductRemoteDataSource(val apiService: ApiService) : ProductDateSource {
    override fun getProducts(): Single<List<Product>> = apiService.getProducts()

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