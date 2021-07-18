package ir.at.nikestore.common

import androidx.annotation.StringRes

class NikeExeption(val type : Type ,
                   @StringRes val userFriendlyMassage : Int = 0 ,
                   val serverMassage : String? = null) : Throwable() {

    enum class Type{
        SIMPLE, DIALOG, AUTH
    }

}