package ir.at.nikestore.feature.auth

import io.reactivex.Completable
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.data.repo.UserRepository

class AuthViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    fun login(email : String , password : String) : Completable{
        progressBarLiveData.value = true
        return userRepository.login(email , password).doFinally { progressBarLiveData.postValue(false) }
    }

    fun signUp(email : String , password : String) : Completable{
        progressBarLiveData.value = true
        return userRepository.signUp(email , password).doFinally { progressBarLiveData.postValue(false) }
    }

}