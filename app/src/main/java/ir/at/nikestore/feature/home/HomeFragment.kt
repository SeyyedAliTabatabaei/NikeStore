package ir.at.nikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevenlearn.nikestore.common.convertDpToPixel
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
import ir.at.nikestore.common.EXTRA_KEY_DATA
import ir.at.nikestore.data.Product
import ir.at.nikestore.data.SORT_LATEST
import ir.at.nikestore.feature.common.ProductListAdapter
import ir.at.nikestore.feature.common.VIEW_TYPE_ROUND
import ir.at.nikestore.feature.list.ProductListActivity
import ir.at.nikestore.feature.main.BannerSliderAdapter
import ir.at.nikestore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : NikeFragment() , ProductListAdapter.ProductEventListener {

    val homeViewModel : HomeViewModel by viewModel()
    val producteListAdapter : ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ROUND) }
    val productePopularListAdapter : ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProductsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        latestProductsRv.adapter = producteListAdapter
        producteListAdapter.productOnClickListener = this

        popurlarProductsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        popurlarProductsRv.adapter = productePopularListAdapter
        productePopularListAdapter.productOnClickListener = this

        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            producteListAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.productsPopularLiveData.observe(viewLifecycleOwner){
            productePopularListAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(32f , requireContext())) * 173) / 328).toInt()
            val layoutParam = bannerSliderViewPager.layoutParams
            layoutParam.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParam

            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }

        viewLatestProductBtn.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA , SORT_LATEST)
            })
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorites(product)
    }
}