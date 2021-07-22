package ir.at.nikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.at.nikestore.NikeFragment
import ir.at.nikestore.R
import ir.at.nikestore.feature.auth.AuthActivity
import ir.at.nikestore.feature.favorite.FavoriteProductsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {

    val viewModel : ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile , container , false)
    }

    override fun onResume() {
        super.onResume()
        checkAuthSatet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext() , FavoriteProductsActivity::class.java))
        }

    }

    private fun checkAuthSatet(){
        if (viewModel.isSingedIn){
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds( 0 ,0 , R.drawable.ic_sign_out , 0)
            usernameTv.text = viewModel.username
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthSatet()
            }
        }else{
            authBtn.text = getString(R.string.signIn)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext() , AuthActivity::class.java))
            }
            authBtn.setCompoundDrawablesWithIntrinsicBounds( 0 ,0 , R.drawable.ic_sign_in , 0)
            usernameTv.text = getString(R.string.guest_user)
        }
    }
}