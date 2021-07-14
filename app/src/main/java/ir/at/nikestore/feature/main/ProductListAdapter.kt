package ir.at.nikestore.feature.main

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

class ProductListAdapter(val imageLoadingService: ImageLoadingService): RecyclerView.Adapter<ProductListAdapter.viewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product , parent , false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) = holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface ProductOnClickListener{
        fun onProductClick(product: Product)
    }
}