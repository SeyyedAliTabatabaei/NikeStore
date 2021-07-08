package ir.at.nikestore.data.repo

import com.sevenlearn.nikestore.data.Banner
import io.reactivex.Single
import ir.at.nikestore.data.repo.source.BannerRemoteDataSource

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerRemoteDataSource) : BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}