package ir.at.nikestore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.CorrectionInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import ir.at.nikestore.common.NikeExeption
import ir.at.nikestore.feature.auth.AuthActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeFragment : Fragment() , NikeView{

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext: Context?
        get() = context

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}


abstract class NikeActivity : AppCompatActivity() , NikeView{

    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of CoordinatorLayout")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }


}


interface NikeView{
    val rootView : CoordinatorLayout?
    val viewContext : Context?

    fun setProgressIndicator(mustShow:Boolean){
        rootView?.let {
            viewContext?.let { context ->

                var loadingView = it.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow){
                    loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading , it , false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeExeption: NikeExeption){
        viewContext?.let {

            when(nikeExeption.type){
                NikeExeption.Type.SIMPLE -> showSnackBar(nikeExeption.serverMassage?:it.getString(nikeExeption.userFriendlyMassage))

                NikeExeption.Type.AUTH -> it.startActivity(Intent(it , AuthActivity::class.java))

            }
        }
    }

    fun showSnackBar(message : String , lengh : Int = Snackbar.LENGTH_SHORT){
        rootView?.let {
            Snackbar.make(it , message , lengh).show()
        }
    }

    fun showEmptyState(layoutResId : Int) : View?{
        rootView?.let {
            viewContext?.let {context ->
                var emptyState = it.findViewById<View>(R.id.emptyStateRootView)
                if (emptyState == null){
                    emptyState = LayoutInflater.from(context).inflate(layoutResId , it , false)
                    it.addView(emptyState)
                }

                emptyState.visibility = View.VISIBLE
                return emptyState
            }
        }
        return null
    }
}

abstract class NikeViewModel : ViewModel(){
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
