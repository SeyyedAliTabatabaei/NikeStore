package ir.at.nikestore

import android.app.Application
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.sevenlearn.nikestore.data.Banner
import ir.at.nikestore.data.repo.BannerRepository
import ir.at.nikestore.data.repo.BannerRepositoryImpl
import ir.at.nikestore.data.repo.ProductRepository
import ir.at.nikestore.data.repo.ProductRepositoryImpl
import ir.at.nikestore.data.repo.source.BannerRemoteDataSource
import ir.at.nikestore.data.repo.source.ProductLocalDataSource
import ir.at.nikestore.data.repo.source.ProductRemoteDataSource
import ir.at.nikestore.feature.main.MainViewModel
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

        Timber.plant()

        Fresco.initialize(this)

        val myModules = module {
            single { creatApiServiceInstance() }
            single<ImageLoadingService> { FrescoLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            viewModel{MainViewModel(get() , get())}
        }

        startKoin {
            androidContext(this@App)

            modules(myModules)
        }

    }
}