package ir.at.nikestore.sevices.http

import com.sevenlearn.nikestore.data.Banner
import com.sevenlearn.nikestore.data.Comment
import io.reactivex.Single
import ir.at.nikestore.data.Product
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort:String) : Single<List<Product>>

    @GET("banner/slider")
    fun getBanner() : Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId : Int ) : Single<List<Comment>>
}

fun creatApiServiceInstance() : ApiService{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}