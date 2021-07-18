package ir.at.nikestore.sevices.http

import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.Banner
import com.sevenlearn.nikestore.data.Comment
import com.sevenlearn.nikestore.data.TokenContainer
import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.Single
import ir.at.nikestore.data.AddToCartResponse
import ir.at.nikestore.data.MessageResponse
import ir.at.nikestore.data.Product
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort:String) : Single<List<Product>>

    @GET("banner/slider")
    fun getBanner() : Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId : Int ) : Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject):Single<AddToCartResponse>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject) : Single<TokenResponse>

    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject) : Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject) : Call<TokenResponse>
}

fun creatApiServiceInstance() : ApiService{

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            if (TokenContainer.token != null)
                newRequestBuilder.addHeader("Authorization" , "Bearer ${TokenContainer.token}")

            newRequestBuilder.addHeader("Accept" , "application/json")
            newRequestBuilder.method(oldRequest.method, oldRequest.body)

            return@addInterceptor it.proceed(newRequestBuilder.build())
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}