package ir.at.nikestore.data.repo.source

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ir.at.nikestore.data.Product

@Dao
interface ProductLocalDataSource : ProductDateSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProduct(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorite(product: Product): Completable

    @Delete
    override fun deleteFromFavorite(product: Product): Completable
}