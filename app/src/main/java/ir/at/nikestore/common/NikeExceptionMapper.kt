package ir.at.nikestore.common

import ir.at.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException

class NikeExceptionMapper {

    companion object{

        fun map(throwable: Throwable) : NikeExeption{
            if (throwable is HttpException){
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val error = errorJsonObject.getString("message")

                    return NikeExeption(if (throwable.code() == 401) NikeExeption.Type.AUTH else NikeExeption.Type.SIMPLE , serverMassage = error)

                }catch (exception : Exception){}

            }

            return NikeExeption(NikeExeption.Type.SIMPLE , R.string.unknowError)
        }
    }
}