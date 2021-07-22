package ir.at.nikestore.feature.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevenlearn.nikestore.feature.order.OrderHistoryViewModel
import ir.at.nikestore.NikeActivity
import ir.at.nikestore.R
import kotlinx.android.synthetic.main.activity_order_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivity() {

    val viewModel: OrderHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        orderHistoryRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel.orders.observe(this) {
            orderHistoryRv.adapter = OrderHistoryItemAdapter(this, it)
        }

        toolbarView.onBackButtonClickListener= View.OnClickListener {
            finish()
        }

    }
}