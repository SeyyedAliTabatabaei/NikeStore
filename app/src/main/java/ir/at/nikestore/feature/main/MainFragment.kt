package ir.at.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.concurrent.timer

class MainFragment : NikeFragment() {

    val mainViewModel : MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.productsLiveData.observe(viewLifecycleOwner){

        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner){
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (bannerSliderViewPager.measuredWidth * 173) / 328
            val layoutParam = bannerSliderViewPager.layoutParams
            layoutParam.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParam

        }
    }
}