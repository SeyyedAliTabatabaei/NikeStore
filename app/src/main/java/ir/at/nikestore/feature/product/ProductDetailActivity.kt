package ir.at.nikestore.feature.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.compose.ui.graphics.Paint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.data.Comment
import com.sevenlearn.nikestore.feature.product.CommentAdapter
import ir.at.nikestore.NikeActivity
import ir.at.nikestore.R
import ir.at.nikestore.common.EXTRA_KEY_ID
import ir.at.nikestore.feature.product.comment.CommentListActivity
import ir.at.nikestore.sevices.http.ImageLoadingService
import ir.at.nikestore.view.scroll.ObservableScrollViewCallbacks
import ir.at.nikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : NikeActivity() {

    val productDetailViewModel : ProductDetailViewModel by inject{ parametersOf(intent.extras)}
    val imageLoadingService : ImageLoadingService by inject()
    val commentAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productDetailViewModel.productLiveData.observe(this){
            imageLoadingService.load(productIv , it.image)
            titleTv.text = it.title
            previousPriceTv.text = formatPrice(it.previous_price)
            previousPriceTv.paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }

        productDetailViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        productDetailViewModel.commentLiveData.observe(this){
            commentAdapter.comments = it as ArrayList<Comment>
            if(it.size > 3){
                viewAllCommentsBtn.visibility = View.VISIBLE

                viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this , CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID , productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }
        }

        initViews()
    }

    fun initViews() {
        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter
        commentsRv.isNestedScrollingEnabled = false

        productIv.post {
            val productIvHeight = productIv.height
            val toolbar = toolbarView
            val productImageView = productIv
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }
    }
}