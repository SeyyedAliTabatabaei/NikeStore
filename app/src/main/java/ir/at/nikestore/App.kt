package ir.at.nikestore

import android.app.Application
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.sevenlearn.nikestore.feature.product.comment.CommentListViewModel
import ir.at.nikestore.data.repo.*
import ir.at.nikestore.data.repo.source.*
import ir.at.nikestore.feature.common.ProductListAdapter
import ir.at.nikestore.feature.list.ProductListViewModel
import ir.at.nikestore.feature.home.HomeViewModel
import ir.at.nikestore.feature.product.ProductDetailViewModel
import ir.at.nikestore.sevices.FrescoLoadingService
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
            factory { (viewType : Int ) -> ProductListAdapter(viewType , get()) }
            single<ImageLoadingService> { FrescoLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }

            viewModel{ HomeViewModel(get() , get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle , get()) }
            viewModel { (productID : Int) -> CommentListViewModel(productID , get()) }
            viewModel { (sort : Int ) -> ProductListViewModel(sort , get()) }

        }

        startKoin {
            androidContext(this@App)

            modules(myModules)
        }

    }
}