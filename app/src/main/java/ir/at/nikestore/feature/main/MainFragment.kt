package ir.at.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevenlearn.nikestore.common.convertDpToPixel
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
import ir.at.nikestore.data.Product
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NikeFragment() {

    val mainViewModel : MainViewModel by viewModel()
    val producteListAdapter : ProductListAdapter by inject()
    val productePopularListAdapter : ProductListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProductsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        latestProductsRv.adapter = producteListAdapter

        popurlarProductsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        popurlarProductsRv.adapter = productePopularListAdapter

        mainViewModel.productsLiveData.observe(viewLifecycleOwner){
            producteListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.productsPopularLiveData.observe(viewLifecycleOwner){
            productePopularListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner){
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(32f , requireContext())) * 173) / 328).toInt()
            val layoutParam = bannerSliderViewPager.layoutParams
            layoutParam.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParam

            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }
    }
}