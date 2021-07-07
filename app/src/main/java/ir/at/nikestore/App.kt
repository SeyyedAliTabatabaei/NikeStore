package ir.at.nikestore

import android.app.Application
import ir.at.nikestore.data.repo.ProductRepository
import ir.at.nikestore.data.repo.ProductRepositoryImpl
import ir.at.nikestore.data.repo.source.ProductLocalDataSource
import ir.at.nikestore.data.repo.source.ProductRemoteDataSource
import ir.at.nikestore.feature.main.MainViewModel
import ir.at.nikestore.sevices.http.ApiService
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

        val myModules = module {
            single { creatApiServiceInstance() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource()) }
            viewModel{MainViewModel(get())}
        }

        startKoin {
            androidContext(this@App)

            modules(myModules)
        }

    }
}