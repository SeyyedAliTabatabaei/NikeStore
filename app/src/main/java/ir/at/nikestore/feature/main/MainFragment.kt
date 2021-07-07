package ir.at.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
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
            Timber.i("aaaaaaaaaaaaaaaaaaaaaaaaa ${it.size}")

        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }
    }
}