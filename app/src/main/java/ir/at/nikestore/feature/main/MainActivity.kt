package ir.at.nikestore.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.sevenlearn.nikestore.common.convertDpToPixel
import ir.at.nikestore.R
import ir.at.nikestore.data.CartItemCount
import ir.at.nikestore.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()

    }

    private fun setupBottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationMain)
        bottomNavigationView.setupWithNavController(navController)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
        val badge = bottomNavigationMain.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        badge.backgroundColor = MaterialColors.getColor(bottomNavigationMain, R.attr.colorPrimary)
        badge.number = cartItemCount.count
        badge.verticalOffset= convertDpToPixel(10f,this).toInt()
        badge.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()
    }
}