package ir.at.nikestore.sevices.http

import ir.at.nikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView:NikeImageView , imageUri :String)
}