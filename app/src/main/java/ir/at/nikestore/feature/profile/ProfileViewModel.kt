package ir.at.nikestore.feature.profile

import com.sevenlearn.nikestore.data.TokenContainer
import ir.at.nikestore.NikeViewModel
import ir.at.nikestore.data.repo.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    val username : String
        get() = userRepository.getUsername()

    val isSingedIn : Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOut()
}