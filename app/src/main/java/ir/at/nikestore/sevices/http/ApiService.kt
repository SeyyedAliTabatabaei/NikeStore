package ir.at.nikestore.sevices.http

import com.sevenlearn.nikestore.data.Banner
import io.reactivex.Single
import ir.at.nikestore.data.Product
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("product/list")
    fun getProducts() : Single<List<Product>>

    @GET("banner/slider")
    fun getBanner() : Single<List<Banner>>
}

fun creatApiServiceInstance() : ApiService{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}