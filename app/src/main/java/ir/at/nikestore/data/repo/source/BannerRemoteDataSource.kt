package ir.at.nikestore.data.repo.source

import com.sevenlearn.nikestore.data.Banner
import io.reactivex.Single
import ir.at.nikestore.sevices.http.ApiService

class BannerRemoteDataSource(val apiService: ApiService) : BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanner()
}