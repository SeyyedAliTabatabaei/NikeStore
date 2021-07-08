package ir.at.nikestore.sevices

import com.facebook.drawee.view.SimpleDraweeView
import ir.at.nikestore.sevices.http.ImageLoadingService
import ir.at.nikestore.view.NikeImageView

class FrescoLoadingService : ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUri: String) {
        if(imageView is SimpleDraweeView){
            imageView.setImageURI(imageUri)
        }
    }
}