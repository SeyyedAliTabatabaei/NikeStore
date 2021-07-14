package ir.at.nikestore

import android.app.Application
import android.os.Bundle
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.sevenlearn.nikestore.data.Banner
import com.sevenlearn.nikestore.feature.product.comment.CommentListViewModel
import ir.at.nikestore.data.repo.*
import ir.at.nikestore.data.repo.source.*
import ir.at.nikestore.feature.main.MainViewModel
import ir.at.nikestore.feature.main.ProductListAdapter
import ir.at.nikestore.feature.product.ProductDetailViewModel
import ir.at.nikestore.sevices.FrescoLoadingService
import ir.at.nikestore.sevices.http.ApiService
import ir.at.nikestore.sevices.http.ImageLoadingService
import ir.at.nikestore.sevices.http.creatApiServiceInstance
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
            factory { ProductListAdapter(get()) }
            single<ImageLoadingService> { FrescoLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }

            viewModel{MainViewModel(get() , get())}
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle , get()) }
            viewModel { (productID : Int) -> CommentListViewModel(productID , get()) }
        }

        startKoin {
            androidContext(this@App)

            modules(myModules)
        }

    }
}