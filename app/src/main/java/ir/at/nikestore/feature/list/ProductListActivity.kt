package ir.at.nikestore.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.at.nikestore.NikeActivity
import ir.at.nikestore.R
import ir.at.nikestore.common.EXTRA_KEY_DATA
import ir.at.nikestore.data.Product
import ir.at.nikestore.feature.common.ProductListAdapter
import ir.at.nikestore.feature.common.VIEW_TYPE_LARGE
import ir.at.nikestore.feature.common.VIEW_TYPE_SMALL
import ir.at.nikestore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : NikeActivity() , ProductListAdapter.ProductEventListener{

    val viewModel : ProductListViewModel by viewModel{ parametersOf(intent.extras!!.getInt(EXTRA_KEY_DATA))}

    val productListAdapter : ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val gridLayout = GridLayoutManager(this , 2)
        productsRv.layoutManager = gridLayout
        productsRv.adapter = productListAdapter

        productListAdapter.productOnClickListener = this

        viewTypeChangedBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL){
                viewTypeChangedBtn.setImageResource(R.drawable.ic_plus_square)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayout.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            }
            else{
                viewTypeChangedBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayout.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        viewModel.productsLiveData.observe(this){
            productListAdapter.products = it as ArrayList<Product>
        }

        viewModel.selectedSortTitleLiveData.observe(this){
            selectedSortTitleTv.text = getString(it)
        }

        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sort , viewModel.sort) { dialog, selectedSortIndex ->
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                    dialog.dismiss()
                }
                .setTitle(getString(R.string.sort))
            dialog.show()
        }

        toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {
        viewModel.addProductToFavorites(product)
    }
}