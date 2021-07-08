package ir.at.nikestore.common

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class NikeSingleObserver<T>(val compositeDisposable: CompositeDisposable) : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(compositeDisposable)
    }

    override fun onError(e: Throwable) {
        TODO("Not yet implemented")
    }
}