package ir.at.nikestore.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
import ir.at.nikestore.common.EXTRA_KEY_DATA
import ir.at.nikestore.common.NikeCompletableObserver
import ir.at.nikestore.data.CartItem
import ir.at.nikestore.feature.product.ProductDetailActivity
import ir.at.nikestore.sevices.http.ImageLoadingService
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : NikeFragment() , CartItemAdapter.CartItemViewCallBacks {

    val viewModel : CartViewModel by viewModel()
    val imageLoadingService: ImageLoadingService by inject()
    var cartItemAdapter : CartItemAdapter? = null
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)
        }

        viewModel.cartItemsLiveData.observe(viewLifecycleOwner){
            cartItemsRv.layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
            cartItemAdapter = CartItemAdapter(it as MutableList<CartItem> , imageLoadingService , this)
            cartItemsRv.adapter = cartItemAdapter
        }

        viewModel.purchaseDetailLiveData.observe(viewLifecycleOwner){

            cartItemAdapter?.let {adapter ->
                adapter.purchaseDetail =it
                adapter.notifyItemChanged(adapter.cartItems.size)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onRemoveCartItem(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.removeCartItem(cartItem)
                }
            })
    }

    override fun onIncreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.increaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.increaseCount(cartItem)
                }
            })
    }

    override fun onDecreaseCartItemButtonClick(cartItem: CartItem) {
        viewModel.decreaseCartItemCount(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartItemAdapter?.decreaseCount(cartItem)
                }
            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , cartItem.product)
        })
    }
}