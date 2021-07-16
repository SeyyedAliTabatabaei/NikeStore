package ir.at.nikestore.feature.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sevenlearn.nikestore.common.formatPrice
import com.sevenlearn.nikestore.common.implementSpringAnimationTrait
import ir.at.nikestore.R
import ir.at.nikestore.data.Product
import ir.at.nikestore.sevices.http.ImageLoadingService
import ir.at.nikestore.view.NikeImageView
const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(var viewType : Int = VIEW_TYPE_ROUND, val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<ProductListAdapter.viewHolder>() {

    var products = ArrayList<Product>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var productOnClickListener : ProductOnClickListener?=null

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv : TextView = itemView.findViewById(R.id.productTitleTv)
        val productIv : NikeImageView = itemView.findViewById(R.id.productIv)
        val currentPriceTv : TextView = itemView.findViewById(R.id.currentPriceTv)
        val previousPriceTv : TextView = itemView.findViewById(R.id.previousPriceTv)

        fun bindProduct(product: Product){
            imageLoadingService.load(productIv , product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener { productOnClickListener?.onProductClick(product) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutResId = when(viewType){
            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("error")
        }
        return viewHolder(LayoutInflater.from(parent.context).inflate(layoutResId , parent , false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) = holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface ProductOnClickListener{
        fun onProductClick(product: Product)
    }
}