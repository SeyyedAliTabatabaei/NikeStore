package ir.at.nikestore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.sevenlearn.nikestore.feature.checkout.CheckoutViewModel
import com.sevenlearn.nikestore.feature.order.OrderHistoryViewModel
import com.sevenlearn.nikestore.feature.product.comment.CommentListViewModel
import ir.at.nikestore.data.db.AppDatabase
import ir.at.nikestore.data.repo.*
import ir.at.nikestore.data.repo.order.OrderRemoteDataSource
import ir.at.nikestore.data.repo.order.OrderRepository
import ir.at.nikestore.data.repo.order.OrderRepositoryImpl
import ir.at.nikestore.data.repo.source.*
import ir.at.nikestore.feature.auth.AuthViewModel
import ir.at.nikestore.feature.cart.CartViewModel
import ir.at.nikestore.feature.common.ProductListAdapter
import ir.at.nikestore.feature.favorite.FavoriteProductsViewModel
import ir.at.nikestore.feature.list.ProductListViewModel
import ir.at.nikestore.feature.home.HomeViewModel
import ir.at.nikestore.feature.main.MainViewModel
import ir.at.nikestore.feature.product.ProductDetailViewModel
import ir.at.nikestore.feature.profile.ProfileViewModel
import ir.at.nikestore.feature.shipping.ShippingViewModel
import ir.at.nikestore.sevices.FrescoLoadingService
import ir.at.nikestore.sevices.http.ImageLoadingService
import ir.at.nikestore.sevices.http.creatApiServiceInstance
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single { creatApiServiceInstance() }

            single<SharedPreferences> { this@App.getSharedPreferences("app_settings" , MODE_PRIVATE) }
            single { UserLocalDataSource(get()) }
            single<UserRepository> { UserRepositoryImpl(UserLocalDataSource(get()) , UserRemoteDataSource(get())) }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            single { Room.databaseBuilder(this@App , AppDatabase::class.java , "db_nike").build() }

            factory { (viewType : Int ) -> ProductListAdapter(viewType , get()) }
            single<ImageLoadingService> { FrescoLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , get<AppDatabase>().productDao()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            viewModel{ HomeViewModel(get() , get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle , get() , get()) }
            viewModel { (productID : Int) -> CommentListViewModel(productID , get()) }
            viewModel { (sort : Int ) -> ProductListViewModel(sort , get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId : Int) -> CheckoutViewModel(orderId , get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }
            viewModel { OrderHistoryViewModel(get()) }

        }

        startKoin {
            androidContext(this@App)

            modules(myModules)
        }


        val userRepository : UserRepository = get()
        userRepository.loadToken()
    }
}