package ir.at.nikestore.data.repo

import android.os.TokenWatcher
import com.sevenlearn.nikestore.data.TokenContainer
import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.Completable
import ir.at.nikestore.data.repo.source.UserDataSource
import ir.at.nikestore.data.repo.source.UserLocalDataSource
import ir.at.nikestore.data.repo.source.UserRemoteDataSource

class UserRepositoryImpl(val userLocalDataSource: UserDataSource ,
                         val userRemoteDataSource: UserDataSource) : UserRepository {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username, password).doOnSuccess {
            onSuccessfulLogin(username ,it)
        }.ignoreElement()
    }

    override fun signUp(username: String, password: String): Completable {
        return userRemoteDataSource.signUp(username, password).flatMap {
            userRemoteDataSource.login(username, password)
        }.doOnSuccess {
            onSuccessfulLogin(username , it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUsername(): String = userLocalDataSource.getUsername()

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null , null)
    }

    fun onSuccessfulLogin(username: String ,tokenResponse: TokenResponse){
        TokenContainer.update(tokenResponse.access_token , tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token , tokenResponse.refresh_token)
        userLocalDataSource.saveUsername(username)
    }
}