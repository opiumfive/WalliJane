package com.iterika.walli.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : IView> : IPresenter<V> {

    private val compositeDisposable = CompositeDisposable()

    protected var view : V? = null

    override fun takeView(view: V) {
        this.view = view
    }

    override fun dropView() {
        compositeDisposable.dispose()
        view = null
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}
